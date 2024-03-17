package com.example.waterSports.controller;

import com.example.waterSports.modal.ActivityLog;
import com.example.waterSports.modal.OrderDetailsWaterSport;
import com.example.waterSports.modal.Referee;
import com.example.waterSports.repo.*;
import com.example.waterSports.modal.OrderWaterSport;
import com.example.waterSports.utils.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/water/")
public class WaterSportController
{
    @Autowired
    OrderWaterSportRepo repoOrder;
    @Autowired
    OrderDetailsWaterSportRepo repoOrderDet;
    @Autowired
    ConfigRepo configRepo;
    @Autowired
    ActivityLogRepo repoActivityLog;
    @Autowired
    RefereeRepo repoRef;

    @GetMapping("/")
    public String showData(Model model)
    {

    //    LocalDate dateFrom = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), 1);
        LocalDate dateFrom = LocalDate.now();
        LocalDate dateTo = LocalDate.now();

        List<OrderWaterSport> list = repoOrder.findByDateBetweenOrderByBillNoDesc(dateFrom, dateTo);
        List<Referee> listRef = repoRef.findAll();
        model.addAttribute("list", list);
        model.addAttribute("listRef", listRef);
        model.addAttribute("repoRef", repoRef);
        model.addAttribute("repoOrderDet", repoOrderDet);
        model.addAttribute("dateFrom", dateFrom);
        model.addAttribute("dateTo", dateTo);
        model.addAttribute("title",configRepo.findOneByProp("title").getVal());
        model.addAttribute("header", configRepo.findOneByProp("header").getVal());
        model.addAttribute("footer", configRepo.findOneByProp("footer").getVal());
        model.addAttribute("contact", configRepo.findOneByProp("contact").getVal());
        model.addAttribute("address", configRepo.findOneByProp("address").getVal());
        model.addAttribute("printer", configRepo.findOneByProp("printer").getVal());
        model.addAttribute("receiptWidth", configRepo.findOneByProp("receiptWidth").getVal());

        return "waterSport";
    }

    @PostMapping("/find/")
    public String findData(LocalDate dateFrom, LocalDate dateTo, Model model)
    {
        if(dateFrom == null)
        {
            dateFrom = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), 1);
        }
        if(dateTo == null)
        {
            dateTo = LocalDate.now();
        }

        List<OrderWaterSport> list = repoOrder.findByDateBetweenOrderByBillNoDesc(dateFrom, dateTo);
        List<Referee> listRef = repoRef.findAll();
        model.addAttribute("list", list);
        model.addAttribute("listRef", listRef);
        model.addAttribute("repoRef", repoRef);
        model.addAttribute("repoOrderDet", repoOrderDet);
        model.addAttribute("dateFrom", dateFrom);
        model.addAttribute("dateTo", dateTo);
        model.addAttribute("title",configRepo.findOneByProp("title").getVal());
        model.addAttribute("header", configRepo.findOneByProp("header").getVal());
        model.addAttribute("footer", configRepo.findOneByProp("footer").getVal());
        model.addAttribute("contact", configRepo.findOneByProp("contact").getVal());
        model.addAttribute("address", configRepo.findOneByProp("address").getVal());
        model.addAttribute("printer", configRepo.findOneByProp("printer").getVal());
        model.addAttribute("receiptWidth", configRepo.findOneByProp("receiptWidth").getVal());

        return "waterSport";
    }

    @GetMapping("/delete/{id}/")
    public String delete(@PathVariable Long id)
    {
        Long billNo = repoOrder.getReferenceById(id).getBillNo();
        repoOrder.deleteById(id);
        repoOrderDet.deleteByBillNo(billNo);

        repoActivityLog.save(new ActivityLog("WaterSports : Order was Deleted with BillNo = " + billNo));

        return "redirect:/water/";
    }

    @GetMapping("/paid/{billNo}/{checked}/")
    public String setPaymentStatus(@PathVariable Long billNo, @PathVariable Boolean checked)
    {
        OrderWaterSport order = repoOrder.getByBillNo(billNo);
        order.setPaid(checked);

        repoActivityLog.save(new ActivityLog("WaterSports : Record Updated. Bill No " + billNo + ", Payment Status = " + (checked?"Paid":"Pending")));

        return "redirect:/water/";
    }

    @GetMapping("/order/{billNo}/")
    @ResponseBody
    public OrderWaterSport getOrder(@PathVariable Long billNo)
    {
        return repoOrder.getByBillNo(billNo);
    }

    @GetMapping("/orderdet/{billNo}/")
    @ResponseBody
    public List<OrderDetailsWaterSport> getOrderDet(@PathVariable Long billNo)
    {
        return repoOrderDet.findByBillNo(billNo);
    }

    @GetMapping("/order/delete/{id}/")
    @ResponseBody
    public Integer deleteOrderDet(@PathVariable Long id)
    {
        Integer status = 0;
        OrderDetailsWaterSport orderDet = repoOrderDet.getReferenceById(id);
        repoOrderDet.deleteById(id);
        status = 1;

        repoActivityLog.save(new ActivityLog("WaterSports : OrderDetails were Deleted with BillNo = " + orderDet.getBillNo() + ", Activity = " + Helper.arrayActivity[orderDet.getIdActivity()-1] + ", persons = " + orderDet.getPersons() + ", rate = " + orderDet.getRate()));

        return status;
    }

    @PostMapping("/add/")
    @ResponseBody
    public OrderDetailsWaterSport addOrderDet(Model model, Long billNo, String customerName, Double rate, Integer persons, Integer idActivity, @RequestParam(defaultValue = "false") Boolean bigRound, @RequestParam(defaultValue = "false") Boolean paid, Long idRef, String receiptNo)
    {
        System.out.println("billNo = " + billNo);
        OrderWaterSport orderSaved;

        if(billNo == null) // For New Bill
        {
            orderSaved = repoOrder.findTopByOrderByBillNoDesc();

            if(orderSaved == null)
            {
                billNo = 1L;
            }
            else
            {
                billNo = orderSaved.getBillNo() + 1;
            }

            OrderWaterSport order = new OrderWaterSport(billNo, customerName, idRef, receiptNo, paid);
            repoOrder.save(order);
        }

        OrderDetailsWaterSport orderDetails = repoOrderDet.findByBillNoAndIdActivity(billNo, idActivity);

        if(orderDetails == null)
        {
            orderDetails = new OrderDetailsWaterSport(billNo, idActivity, bigRound, persons, rate);
        }
        else
        {
            orderDetails.setBigRound(bigRound);
            orderDetails.setPersons(persons);
            orderDetails.setRate(rate);
        }
        OrderDetailsWaterSport orderDetailsSaved = repoOrderDet.save(orderDetails);

        repoActivityLog.save(new ActivityLog("WaterSports : OrderDetails were Added with BillNo = " + billNo + ", Activity = " + Helper.arrayActivity[idActivity-1] + ", persons = " + persons + ", rate = " + rate + ", customer = " + customerName + ", referee = " + repoRef.getReferenceById(idRef).getName()));

        return orderDetailsSaved;
    }

    @GetMapping("/print/{id}/")
    @ResponseBody
    public Integer printBill(@PathVariable Long id)
    {
        Integer status = 0;

        OrderWaterSport order = repoOrder.getByBillNo(id);

        System.out.println("order = " + order.toString());

        List<OrderDetailsWaterSport> listOrderDet = repoOrderDet.findByBillNo(order.getBillNo());

        System.out.println("orderDet = " + listOrderDet.toString());

        Referee ref = repoRef.getReferenceById(order.getIdRef());
        String refName = ref.getName();
        String ownerName = repoRef.getReferenceById(ref.getIdOwner()).getName();

        status = Helper.PrintBill(
                configRepo.findOneByProp("title").getVal(),
                configRepo.findOneByProp("header").getVal(),
                configRepo.findOneByProp("footer").getVal(),
                configRepo.findOneByProp("address").getVal(),
                configRepo.findOneByProp("contact").getVal(),
                configRepo.findOneByProp("printer").getVal(),
                order.getBillNo(),
                refName,
                ownerName,
                order.getCustomerName(),
                null,
                null,
                null,
                Helper.formatter.format(order.getDate()),
                listOrderDet,
                Integer.parseInt(configRepo.findOneByProp("receiptWidth").getVal())
        );

        return status;
    }

}
