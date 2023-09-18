package com.example.waterSports.controller;

import com.example.waterSports.modal.OrderWaterSport;
import com.example.waterSports.repo.ConfigRepo;
import com.example.waterSports.repo.OrderWaterSportRepo;
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
@RequestMapping("/water/")
public class WaterSportController
{
    @Autowired
    OrderWaterSportRepo repo;
    @Autowired
    ConfigRepo configRepo;

    @GetMapping("/")
    public String showData(Model model)
    {

        LocalDate dateFrom = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), 1);
        LocalDate dateTo = LocalDate.now();

        List<OrderWaterSport> list = repo.findByDateBetweenOrderByBillNoDesc(dateFrom, dateTo);
        model.addAttribute("list", list);
        model.addAttribute("dateFrom", dateFrom);
        model.addAttribute("dateTo", dateTo);
        model.addAttribute("title",configRepo.findOneByProp("title").getVal());
        model.addAttribute("header", configRepo.findOneByProp("header").getVal());
        model.addAttribute("footer", configRepo.findOneByProp("footer").getVal());
        model.addAttribute("contact", configRepo.findOneByProp("contact").getVal());
        model.addAttribute("address", configRepo.findOneByProp("address").getVal());

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

        List<OrderWaterSport> list = repo.findByDateBetweenOrderByBillNoDesc(dateFrom, dateTo);
        model.addAttribute("list", list);
        model.addAttribute("dateFrom", dateFrom);
        model.addAttribute("dateTo", dateTo);
        model.addAttribute("title",configRepo.findOneByProp("title").getVal());
        model.addAttribute("header", configRepo.findOneByProp("header").getVal());
        model.addAttribute("footer", configRepo.findOneByProp("footer").getVal());
        model.addAttribute("contact", configRepo.findOneByProp("contact").getVal());
        model.addAttribute("address", configRepo.findOneByProp("address").getVal());

        return "waterSport";
    }

    @GetMapping("/delete/{id}/")
    public String delete(@PathVariable Long id)
    {
        repo.deleteById(id);
        return "redirect:/water/";
    }

    @PostMapping("/")
    public String findData(Model model, String customerName, String contact, Double rate, Integer nPerson, Boolean jsr, Boolean br, Boolean sebr, Boolean slbr)
    {
        OrderWaterSport orderSaved = repo.findTopByOrderByBillNoDesc();
        Long maxBillNo = null;

        if(orderSaved == null)
        {
            maxBillNo = 0L;
        }
        else
        {
            maxBillNo = orderSaved.getBillNo();
        }

        OrderWaterSport order = new OrderWaterSport(maxBillNo + 1, customerName, contact, rate, nPerson, jsr, br, sebr, slbr);
        System.out.println("Order to be saved = " + order.toString());
        repo.save(order);
        
        return "redirect:/water/";
    }
}
