package com.example.waterSports.controller;

import com.example.waterSports.modal.Report;
import com.example.waterSports.repo.ConfigRepo;
import com.example.waterSports.repo.OrderDetailsWaterSportRepo;
import com.example.waterSports.repo.OrderParasalingRepo;
import com.example.waterSports.repo.RefereeRepo;
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
    @Autowired
    RefereeRepo repoRef;


    @PostMapping("/")
    public String showReport(Model model, LocalDate dateFrom, LocalDate dateTo, Integer idGroup, Integer idReport)
    {
        List<Report> list = null;

        if(idReport == 0) // Watersports
        {
            if(idGroup == 0)
            {
                list = orderWatersportRepo.getDailyReportWaterSport(dateFrom, dateTo.plusDays(1));
            }
            else if(idGroup == 1)
            {
                list = orderWatersportRepo.getMonthlyReportWaterSport(dateFrom, dateTo.plusDays(1));
            }
            else if(idGroup == 2)
            {
                list = orderWatersportRepo.getYearlyReportWaterSport(dateFrom, dateTo.plusDays(1));
            }
        }
        else if(idReport == 1) // Parasailing
        {
            if(idGroup == 0)
            {
                list = orderParasalingRepo.getDailyReportWaterSport(dateFrom, dateTo.plusDays(1));
            }
            else if(idGroup == 1)
            {
                list = orderParasalingRepo.getMonthlyReportWaterSport(dateFrom, dateTo.plusDays(1));
            }
            else if(idGroup == 2)
            {
                list = orderParasalingRepo.getYearlyReportWaterSport(dateFrom, dateTo.plusDays(1));
            }
        }
        else if(idReport == 2) // Watersport Referee Report
        {
            list = orderWatersportRepo.getReportReferee(dateFrom, dateTo.plusDays(1));
        }
        else if(idReport == 3) // Parasailing Referee Report
        {
            list = orderParasalingRepo.getReportReferee(dateFrom, dateTo.plusDays(1));
        }

        model.addAttribute("list", list);
        model.addAttribute("repoRef", repoRef);
        model.addAttribute("repoWS", orderWatersportRepo);
        model.addAttribute("repoPar", orderParasalingRepo);
        model.addAttribute("dateFrom", dateFrom);
        model.addAttribute("dateTo", dateTo);
        model.addAttribute("idGroup", idGroup);
        model.addAttribute("idReport", idReport);
        model.addAttribute("arrayMonth", Helper.arrayMonth);
        model.addAttribute("title", configRepo.findOneByProp("title").getVal());

        return "report";
    }
}
