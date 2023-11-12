package com.example.waterSports.controller;

import com.example.waterSports.modal.Report;
import com.example.waterSports.repo.ConfigRepo;
import com.example.waterSports.repo.OrderDetailsWaterSportRepo;
import com.example.waterSports.repo.OrderParasalingRepo;
import com.example.waterSports.utils.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/report/")
public class ReportController
{
    @Autowired
    OrderDetailsWaterSportRepo orderWatersportRepo;

    @Autowired
    OrderParasalingRepo orderParasalingRepo;

    @Autowired
    ConfigRepo configRepo;


    @PostMapping("/")
    public String showReport(Model model, LocalDate dateFrom, LocalDate dateTo, Integer idGroup, Integer idActivity)
    {
        List<Report> list = null;

        if(idActivity == 0) // Watersports
        {
            if(idGroup == 0)
            {
                list = orderWatersportRepo.getDailyReportWaterSport(dateFrom, dateTo);
            }
            else if(idGroup == 1)
            {
                list = orderWatersportRepo.getMonthlyReportWaterSport(dateFrom, dateTo);
            }
            else if(idGroup == 2)
            {
                list = orderWatersportRepo.getYearlyReportWaterSport(dateFrom, dateTo);
            }
        }
        else if(idActivity == 1) // Parasailing
        {
            if(idGroup == 0)
            {
                list = orderParasalingRepo.getDailyReportWaterSport(dateFrom, dateTo);
            }
            else if(idGroup == 1)
            {
                list = orderParasalingRepo.getMonthlyReportWaterSport(dateFrom, dateTo);
            }
            else if(idGroup == 2)
            {
                list = orderParasalingRepo.getYearlyReportWaterSport(dateFrom, dateTo);
            }
        }

        model.addAttribute("list", list);
        model.addAttribute("dateFrom", dateFrom);
        model.addAttribute("dateTo", dateTo);
        model.addAttribute("idGroup", idGroup);
        model.addAttribute("idActivity", idActivity);
        model.addAttribute("arrayMonth", Helper.arrayMonth);
        model.addAttribute("title", configRepo.findOneByProp("title").getVal());

        return "report";
    }
}
