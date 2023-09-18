package com.example.waterSports.controller;

import com.example.waterSports.modal.OrderParasailing;
import com.example.waterSports.repo.ConfigRepo;
import com.example.waterSports.repo.OrderParasalingRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
        System.out.println("Order to be saved = " + order.toString());
        repo.save(order);

        return "redirect:/para/";
    }
}
