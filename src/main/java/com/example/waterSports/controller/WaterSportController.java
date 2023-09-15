package com.example.waterSports.controller;

import com.example.waterSports.modal.OrderWaterSport;
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

    @GetMapping("/")
    public String showData(Model model)
    {

        LocalDate startDate = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), 1);
        LocalDate endDate = LocalDate.now();

        List<OrderWaterSport> list = repo.findByDateBetween(startDate, endDate);
        model.addAttribute("list", list);
        return "waterSport";
    }

    @PostMapping("/find/")
    public String findData(LocalDate startDate, LocalDate endDate, Model model)
    {
        if(startDate == null)
        {
            startDate = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), 1);
        }
        if(endDate == null)
        {
            endDate = LocalDate.now();
        }

        List<OrderWaterSport> list = repo.findByDateBetween(startDate, endDate);
        model.addAttribute("list", list);
        return "waterSport";
    }

    @GetMapping("/delete/{id}/")
    public String delete(@PathVariable Long id)
    {
        repo.deleteById(id);
        return "redirect:/water/";
    }

    @PostMapping("/")
    public String findData(Model model, String customerName, String contact, Double rate, Integer nPerson, Boolean jsr, Boolean br, Boolean sebr, Boolean slbr, Boolean para)
    {
        Long maxBillNo = repo.findTopByOrderByBillNoDesc();

        if(maxBillNo == null)
        {
            maxBillNo = 1L;
        }

        OrderWaterSport order = new OrderWaterSport(maxBillNo + 1, customerName, contact, rate, nPerson, jsr, br, sebr, slbr);
        repo.save(order);
        
        return "redirect:/water/";
    }
}
