package com.example.waterSports.controller;

import com.example.waterSports.modal.Referee;
import com.example.waterSports.repo.ActivityLogRepo;
import com.example.waterSports.repo.ConfigRepo;
import com.example.waterSports.repo.RefereeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/ref/")
public class RefereeController
{
    @Autowired
    RefereeRepo repo;
    @Autowired
    ConfigRepo configRepo;
    @Autowired
    ActivityLogRepo activityLogRepo;

    @GetMapping("/")
    public String home(Model model)
    {
        List<Referee> list = repo.findAll();
        model.addAttribute("list", list);
        model.addAttribute("repo", repo);
        model.addAttribute("title",configRepo.findOneByProp("title").getVal());
        model.addAttribute("header", configRepo.findOneByProp("header").getVal());
        model.addAttribute("footer", configRepo.findOneByProp("footer").getVal());
        model.addAttribute("contact", configRepo.findOneByProp("contact").getVal());
        model.addAttribute("address", configRepo.findOneByProp("address").getVal());
        model.addAttribute("printer", configRepo.findOneByProp("printer").getVal());

        return "referee";
    }

    @PostMapping("/")
    public String add(Referee referee)
    {
        Referee refSaved = repo.save(referee);

        if(referee.getIdOwner() == -1)
        {
            refSaved.setIdOwner(refSaved.getId());
            repo.save(refSaved);
        }

        return "redirect:/ref/";
    }

    @GetMapping("/delete/{id}/")
    public String delete(@PathVariable Long id)
    {
        repo.deleteById(id);
        return "redirect:/ref/";
    }
}
