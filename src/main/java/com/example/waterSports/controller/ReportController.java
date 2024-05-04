package com.example.waterSports.controller;

import com.example.waterSports.modal.*;
import com.example.waterSports.repo.*;
import com.example.waterSports.utils.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Controller
@RequestMapping("/report/")
public class ReportController
{
    @Autowired
    OrderDetailsWaterSportRepo orderDetWatersportRepo;
    @Autowired
    OrderWaterSportRepo orderWaterSportRepo;
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
        List<OrderWaterSport> listWsOrder = null;
        List<OrderDetailsWaterSport> listWsOrderDet = null;
        List<OrderParasailing> listPsOrder = null;
        String resultPage = "report";

        model.addAttribute("arrayActivity", Helper.arrayActivity);
        model.addAttribute("repoRef", repoRef);
        model.addAttribute("repoWS", orderDetWatersportRepo);
        model.addAttribute("repoOrderWs", orderWaterSportRepo);
        model.addAttribute("repoPar", orderParasalingRepo);
        model.addAttribute("dateFrom", dateFrom.atStartOfDay());
        model.addAttribute("dateTo", dateTo.atTime(LocalTime.MAX));
        model.addAttribute("idReport", idReport);
        model.addAttribute("arrayMonth", Helper.arrayMonth);
        model.addAttribute("title", configRepo.findOneByProp("title").getVal());

        if(idReport == 0)
        {
            listReport = orderDetWatersportRepo.getDailyReportWaterSport(dateFrom.atStartOfDay(), dateTo.atTime(LocalTime.MAX));
            model.addAttribute("list", listReport);
        }
        else if(idReport == 1)
        {
            listReport = orderDetWatersportRepo.getMonthlyReportWaterSport(dateFrom.atStartOfDay(), dateTo.atTime(LocalTime.MAX));
            model.addAttribute("list", listReport);
        }
        else if(idReport == 2)
        {
            listReport = orderDetWatersportRepo.getYearlyReportWaterSport(dateFrom.atStartOfDay(), dateTo.atTime(LocalTime.MAX));
            model.addAttribute("list", listReport);
        }
        if(idReport == 3)
        {
            listReport = orderParasalingRepo.getDailyReportWaterSport(dateFrom.atStartOfDay(), dateTo.atTime(LocalTime.MAX));
            model.addAttribute("list", listReport);
        }
        else if(idReport == 4)
        {
            listReport = orderParasalingRepo.getMonthlyReportWaterSport(dateFrom.atStartOfDay(), dateTo.atTime(LocalTime.MAX));
            model.addAttribute("list", listReport);
        }
        else if(idReport == 5)
        {
            listReport = orderParasalingRepo.getYearlyReportWaterSport(dateFrom.atStartOfDay(), dateTo.atTime(LocalTime.MAX));
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
            listWsOrder = orderWaterSportRepo.findByDateBetweenOrderByBillNo(dateFrom.atStartOfDay(), dateTo.atTime(LocalTime.MAX));
            model.addAttribute("list", listWsOrder);
            resultPage = "reportMaster";
        }
        else if(idReport == 9)
        {
            listWsOrderDet = orderDetWatersportRepo.findByDateBetweenOrderByBillNo(dateFrom.atStartOfDay(), dateTo.atTime(LocalTime.MAX));
            model.addAttribute("list", listWsOrderDet);
            resultPage = "reportOrderDet";
        }
        else if(idReport == 10)
        {
            listPsOrder = orderParasalingRepo.findByDateBetweenOrderByBillNo(dateFrom.atStartOfDay(), dateTo.atTime(LocalTime.MAX));
            model.addAttribute("list", listPsOrder);
            resultPage = "reportMaster";
        }

        return resultPage;
    }

    @GetMapping("/owner/{idOwner}/{dateFrom}/{dateTo}/")
    public String printOwnerRcpt(Model model, @PathVariable Long idOwner, @PathVariable LocalDateTime dateFrom, @PathVariable LocalDateTime dateTo)
    {
        Integer status = 0;
        System.out.println("dateFrom = " + dateFrom);
        System.out.println("dateTo = " + dateTo);

        List<OrderWaterSport> listOrderWs = orderWaterSportRepo.findReferences(idOwner, dateFrom, dateTo);
        List<OrderParasailing> listOrderPar = orderParasalingRepo.findReferences(idOwner, dateFrom, dateTo);
        model.addAttribute("listOrderWs", listOrderWs);
        model.addAttribute("listOrderPs", listOrderPar);
        model.addAttribute("repoWS", orderDetWatersportRepo);
        model.addAttribute("repoPS", orderParasalingRepo);
        model.addAttribute("repoRef", repoRef);
        model.addAttribute("idOwner", idOwner);
        model.addAttribute("dateFrom", dateFrom);
        model.addAttribute("dateTo", dateTo);
        model.addAttribute("title",configRepo.findOneByProp("title").getVal());

        return "reportOwner";
    }
}
