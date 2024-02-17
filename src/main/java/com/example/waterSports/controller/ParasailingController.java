package com.example.waterSports.controller;

import com.example.waterSports.modal.ActivityLog;
import com.example.waterSports.modal.OrderParasailing;
import com.example.waterSports.modal.OrderWaterSport;
import com.example.waterSports.modal.Referee;
import com.example.waterSports.repo.ActivityLogRepo;
import com.example.waterSports.repo.ConfigRepo;
import com.example.waterSports.repo.OrderParasalingRepo;
import com.example.waterSports.repo.RefereeRepo;
import com.example.waterSports.utils.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/para/")
public class ParasailingController
{
    @Autowired
    OrderParasalingRepo repo;
    @Autowired
    ConfigRepo configRepo;
    @Autowired
    ActivityLogRepo repoActivityLog;
    @Autowired
    RefereeRepo repoRef;

    @GetMapping("/")
    public String showData(Model model)
    {

        LocalDate dateFrom = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), 1);
        LocalDate dateTo = LocalDate.now();

        List<OrderParasailing> list = repo.findByDateBetweenOrderByBillNoDesc(dateFrom, dateTo);
        List<Referee> listRef = repoRef.findAll();
        model.addAttribute("list", list);
        model.addAttribute("listRef", listRef);
        model.addAttribute("repoRef", repoRef);
        model.addAttribute("dateFrom", dateFrom);
        model.addAttribute("dateTo", dateTo);
        model.addAttribute("title",configRepo.findOneByProp("title").getVal());
        model.addAttribute("header", configRepo.findOneByProp("header").getVal());
        model.addAttribute("footer", configRepo.findOneByProp("footer").getVal());
        model.addAttribute("contact", configRepo.findOneByProp("contact").getVal());
        model.addAttribute("address", configRepo.findOneByProp("address").getVal());
        model.addAttribute("printer", configRepo.findOneByProp("printer").getVal());
        model.addAttribute("receiptWidth", configRepo.findOneByProp("receiptWidth").getVal());

        return "paraSailing";
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

        List<OrderParasailing> list = repo.findByDateBetweenOrderByBillNoDesc(dateFrom, dateTo);
        List<Referee> listRef = repoRef.findAll();
        model.addAttribute("list", list);
        model.addAttribute("listRef", listRef);
        model.addAttribute("repoRef", repoRef);
        model.addAttribute("dateFrom", dateFrom);
        model.addAttribute("dateTo", dateTo);
        model.addAttribute("title",configRepo.findOneByProp("title").getVal());
        model.addAttribute("header", configRepo.findOneByProp("header").getVal());
        model.addAttribute("footer", configRepo.findOneByProp("footer").getVal());
        model.addAttribute("contact", configRepo.findOneByProp("contact").getVal());
        model.addAttribute("address", configRepo.findOneByProp("address").getVal());
        model.addAttribute("printer", configRepo.findOneByProp("printer").getVal());
        model.addAttribute("receiptWidth", configRepo.findOneByProp("receiptWidth").getVal());

        return "paraSailing";
    }

    @GetMapping("/delete/{id}/")
    public String delete(@PathVariable Long id)
    {
        OrderParasailing order = repo.getReferenceById(id);
        repo.deleteById(id);
        repoActivityLog.save(new ActivityLog("Parasailing : OrderDetails Deleted with BillNo = " + order.getBillNo() + ", persons = " + order.getnPerson() + ", rate = " + order.getRate() + ", customer = " + order.getCustomerName() + ", contact = " + order.getContact()));

        return "redirect:/para/";
    }

    @PostMapping("/")
    public String addData(Model model, Long id, String customerName, String contact, Double rate, Integer nPerson, Long idRef, String receiptNo, @RequestParam(defaultValue = "false") Boolean bigRound, String serialNo)
    {
        OrderParasailing orderSaved = null;

        System.out.println("isBigRound = " + bigRound);

        if(id != null)
        {
            orderSaved = repo.getReferenceById(id);
            orderSaved.setCustomerName(customerName);
            orderSaved.setContact(contact);
            orderSaved.setRate(rate);
            orderSaved.setnPerson(nPerson);
            orderSaved.setIdRef(idRef);
            orderSaved.setReceiptNo(receiptNo);
            orderSaved.setBigRound(bigRound);
            orderSaved.setSerialNo(serialNo);
            repo.save(orderSaved);

            repoActivityLog.save(new ActivityLog("Parasailing : OrderDetails were Updated with BillNo = " + orderSaved.getBillNo() + 1 + ", persons = " + nPerson + ", rate = " + rate + ", customer = " + orderSaved.getCustomerName() + ", contact = " + orderSaved.getContact() + ", referee = " + repoRef.getReferenceById(idRef).getName() + ", serial No = " + serialNo));
        }
        else
        {
            OrderParasailing topRecord = repo.findTopByOrderByBillNoDesc();
            Long maxBillNo = null;

            if(topRecord == null)
            {
                maxBillNo = 0L;
            }
            else
            {
                maxBillNo = topRecord.getBillNo();
            }

            OrderParasailing order = new OrderParasailing(maxBillNo + 1, customerName, contact, rate, nPerson, idRef, receiptNo, bigRound, serialNo);
            orderSaved = repo.save(order);

            repoActivityLog.save(new ActivityLog("Parasailing : OrderDetails were Added with BillNo = " + maxBillNo + 1 + ", persons = " + nPerson + ", rate = " + rate + ", customer = " + order.getCustomerName() + ", contact = " + order.getContact() + ", referee = " + repoRef.getReferenceById(idRef).getName() + ", serial No = " + serialNo));
        }

        Referee ref = repoRef.getReferenceById(orderSaved.getIdRef());
        String refName = ref.getName();
        String ownerName = repoRef.getReferenceById(ref.getIdOwner()).getName();

        Helper.PrintBill(
                configRepo.findOneByProp("title").getVal(),
                configRepo.findOneByProp("header").getVal(),
                configRepo.findOneByProp("footer").getVal(),
                configRepo.findOneByProp("address").getVal(),
                configRepo.findOneByProp("contact").getVal(),
                configRepo.findOneByProp("printer").getVal(),
                orderSaved.getBillNo(),
                orderSaved.getSerialNo(),
                refName,
                ownerName,
                orderSaved.getCustomerName(),
                orderSaved.isBigRound() ? "Parasailing (Big Round)":"Parasailing",
                orderSaved.getnPerson(),
                orderSaved.getRate(),
                Helper.formatter.format(orderSaved.getDate()),
                null,
                Integer.parseInt(configRepo.findOneByProp("receiptWidth").getVal())
        );

        return "redirect:/para/";
    }

    @GetMapping("/print/{id}/")
    @ResponseBody
    public Integer printBill(@PathVariable Long id)
    {
        Integer status = 0;

        OrderParasailing order = repo.getReferenceById(id);

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
                order.getSerialNo(),
                refName,
                ownerName,
                order.getCustomerName(),
                order.isBigRound() ? "Parasailing (Big Round)":"Parasailing",
                order.getnPerson(),
                order.getRate(),
                Helper.formatter.format(order.getDate()),
                null,
                Integer.parseInt(configRepo.findOneByProp("receiptWidth").getVal())
        );

        return status;
    }
}
