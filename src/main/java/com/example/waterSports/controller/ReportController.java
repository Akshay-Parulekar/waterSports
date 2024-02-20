package com.example.waterSports.controller;

import com.example.waterSports.modal.Referee;
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
    public String showReport(Model model, LocalDate dateFrom, LocalDate dateTo, Integer idReport)
    {
        List<Report> listReport = null;
        List<Referee> listRef = null;
        String resultPage = "report";

        model.addAttribute("repoRef", repoRef);
        model.addAttribute("repoWS", orderWatersportRepo);
        model.addAttribute("repoPar", orderParasalingRepo);
        model.addAttribute("dateFrom", dateFrom);
        model.addAttribute("dateTo", dateTo);
        model.addAttribute("idReport", idReport);
        model.addAttribute("arrayMonth", Helper.arrayMonth);
        model.addAttribute("title", configRepo.findOneByProp("title").getVal());

        if(idReport == 0)
        {
            listReport = orderWatersportRepo.getDailyReportWaterSport(dateFrom, dateTo.plusDays(1));
            model.addAttribute("list", listReport);
        }
        else if(idReport == 1)
        {
            listReport = orderWatersportRepo.getMonthlyReportWaterSport(dateFrom, dateTo.plusDays(1));
            model.addAttribute("list", listReport);
        }
        else if(idReport == 2)
        {
            listReport = orderWatersportRepo.getYearlyReportWaterSport(dateFrom, dateTo.plusDays(1));
            model.addAttribute("list", listReport);
        }
        if(idReport == 3)
        {
            listReport = orderParasalingRepo.getDailyReportWaterSport(dateFrom, dateTo.plusDays(1));
            model.addAttribute("list", listReport);
        }
        else if(idReport == 4)
        {
            listReport = orderParasalingRepo.getMonthlyReportWaterSport(dateFrom, dateTo.plusDays(1));
            model.addAttribute("list", listReport);
        }
        else if(idReport == 5)
        {
            listReport = orderParasalingRepo.getYearlyReportWaterSport(dateFrom, dateTo.plusDays(1));
            model.addAttribute("list", listReport);
        }
        else if(idReport == 6)
        {
            listRef = repoRef.findOwners();
            model.addAttribute("list", listRef);
            resultPage = "reportMarketer";
        }
        else if(idReport == 7)
        {
            listRef = repoRef.findAllByOrderByIdOwnerAscName();
            model.addAttribute("list", listRef);
            resultPage = "reportMarketer";
        }
        else if(idReport == 8)
        {

        }
        else if(idReport == 9)
        {

        }

        return resultPage;
    }
}
