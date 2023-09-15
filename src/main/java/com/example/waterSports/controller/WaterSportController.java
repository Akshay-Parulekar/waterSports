package com.example.waterSports.controller;

import com.example.waterSports.modal.OrderWaterSport;
import com.example.waterSports.repo.OrderWaterSportRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/water/")
public class WaterSportController
{
    @Autowired
    OrderWaterSportRepo waterRepo;

    @GetMapping("/")
    public String showData(LocalDate startDate, LocalDate endDate, Model model)
    {
        if(startDate == null)
        {
            startDate = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), 1);
        }
        if(endDate == null)
        {
            endDate = LocalDate.now();
        }

        List<OrderWaterSport> list = waterRepo.findByDateBetween(startDate, endDate);
        model.addAttribute("list", list);
        return "waterSport";
    }
}
