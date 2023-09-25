package com.example.waterSports.controller;

import com.example.waterSports.modal.OrderParasailing;
import com.example.waterSports.modal.OrderWaterSport;
import com.example.waterSports.repo.ConfigRepo;
import com.example.waterSports.repo.OrderParasalingRepo;
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

    @GetMapping("/")
    public String showData(Model model)
    {

        LocalDate dateFrom = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), 1);
        LocalDate dateTo = LocalDate.now();

        List<OrderParasailing> list = repo.findByDateBetweenOrderByBillNoDesc(dateFrom, dateTo);
        model.addAttribute("list", list);
        model.addAttribute("dateFrom", dateFrom);
        model.addAttribute("dateTo", dateTo);
        model.addAttribute("title",configRepo.findOneByProp("title").getVal());
        model.addAttribute("header", configRepo.findOneByProp("header").getVal());
        model.addAttribute("footer", configRepo.findOneByProp("footer").getVal());
        model.addAttribute("contact", configRepo.findOneByProp("contact").getVal());
        model.addAttribute("address", configRepo.findOneByProp("address").getVal());

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
        model.addAttribute("list", list);
        model.addAttribute("dateFrom", dateFrom);
        model.addAttribute("dateTo", dateTo);
        model.addAttribute("title",configRepo.findOneByProp("title").getVal());
        model.addAttribute("header", configRepo.findOneByProp("header").getVal());
        model.addAttribute("footer", configRepo.findOneByProp("footer").getVal());
        model.addAttribute("contact", configRepo.findOneByProp("contact").getVal());
        model.addAttribute("address", configRepo.findOneByProp("address").getVal());

        return "paraSailing";
    }

    @GetMapping("/delete/{id}/")
    public String delete(@PathVariable Long id)
    {
        repo.deleteById(id);
        return "redirect:/para/";
    }

    @PostMapping("/")
    public String findData(Model model, String customerName, String contact, Double rate, Integer nPerson)
    {
        OrderParasailing orderSaved = repo.findTopByOrderByBillNoDesc();
        Long maxBillNo = null;

        if(orderSaved == null)
        {
            maxBillNo = 0L;
        }
        else
        {
            maxBillNo = orderSaved.getBillNo();
        }

        OrderParasailing order = new OrderParasailing(maxBillNo + 1, customerName, contact, rate, nPerson);
        repo.save(order);

        Helper.PrintBill(
                configRepo.findOneByProp("title").getVal(),
                configRepo.findOneByProp("header").getVal(),
                configRepo.findOneByProp("footer").getVal(),
                configRepo.findOneByProp("address").getVal(),
                configRepo.findOneByProp("contact").getVal(),
                configRepo.findOneByProp("printer").getVal(),
                order.getBillNo(),
                order.getCustomerName(),
                "Parasailing",
                order.getnPerson(),
                order.getRate(),
                Helper.formatter.format(order.getDate())
        );

        return "redirect:/para/";
    }

    @GetMapping("/print/{id}/")
    @ResponseBody
    public Integer printBill(@PathVariable Long id)
    {
        Integer status = 0;

        OrderParasailing order = repo.getReferenceById(id);
        status = Helper.PrintBill(
                configRepo.findOneByProp("title").getVal(),
                configRepo.findOneByProp("header").getVal(),
                configRepo.findOneByProp("footer").getVal(),
                configRepo.findOneByProp("address").getVal(),
                configRepo.findOneByProp("contact").getVal(),
                configRepo.findOneByProp("printer").getVal(),
                order.getBillNo(),
                order.getCustomerName(),
                "Parasailing",
                order.getnPerson(),
                order.getRate(),
                Helper.formatter.format(order.getDate())
        );

        return status;
    }
}
