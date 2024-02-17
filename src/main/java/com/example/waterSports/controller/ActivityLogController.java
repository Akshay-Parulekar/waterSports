package com.example.waterSports.controller;

import com.example.waterSports.modal.ActivityLog;
import com.example.waterSports.repo.ActivityLogRepo;
import com.example.waterSports.repo.ConfigRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Controller
@RequestMapping("/logs/")
public class ActivityLogController
{
    @Autowired
    ActivityLogRepo repo;

    @Autowired
    ConfigRepo configRepo;

    @GetMapping("/")
    public String showData(Model model)
    {
        LocalDateTime dateFrom = LocalDateTime.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), 1, 0, 0);
        LocalDateTime dateTo = LocalDateTime.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), LocalDate.now().getDayOfMonth(), 23, 00);

        List<ActivityLog> list = repo.findByTimestampActBetweenOrderByTimestampActDesc(dateFrom, dateTo);
        model.addAttribute("list", list);
        model.addAttribute("dateFrom", dateFrom);
        model.addAttribute("dateTo", dateTo);
        model.addAttribute("title",configRepo.findOneByProp("title").getVal());
        model.addAttribute("header", configRepo.findOneByProp("header").getVal());
        model.addAttribute("footer", configRepo.findOneByProp("footer").getVal());
        model.addAttribute("contact", configRepo.findOneByProp("contact").getVal());
        model.addAttribute("address", configRepo.findOneByProp("address").getVal());
        model.addAttribute("printer", configRepo.findOneByProp("printer").getVal());

        return "activityLogs";
    }

    @PostMapping("/find/")
    public String findData(LocalDateTime dateFrom, LocalDateTime dateTo, Model model)
    {
        if(dateFrom == null)
        {
            dateFrom = LocalDateTime.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), 1, 0, 0);
        }
        if(dateTo == null)
        {
            dateTo = LocalDateTime.now();
        }

        List<ActivityLog> list = repo.findByTimestampActBetweenOrderByTimestampActDesc(dateFrom, dateTo);
        model.addAttribute("list", list);
        model.addAttribute("dateFrom", dateFrom);
        model.addAttribute("dateTo", dateTo);
        model.addAttribute("title",configRepo.findOneByProp("title").getVal());
        model.addAttribute("header", configRepo.findOneByProp("header").getVal());
        model.addAttribute("footer", configRepo.findOneByProp("footer").getVal());
        model.addAttribute("contact", configRepo.findOneByProp("contact").getVal());
        model.addAttribute("address", configRepo.findOneByProp("address").getVal());
        model.addAttribute("printer", configRepo.findOneByProp("printer").getVal());

        return "activityLogs";
    }

    @GetMapping("/clear/")
    public String clearLogs()
    {
        repo.deleteAll();
        return "redirect:/logs/";
    }
}
