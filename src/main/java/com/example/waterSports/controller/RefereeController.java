package com.example.waterSports.controller;

import com.example.waterSports.modal.ActivityLog;
import com.example.waterSports.modal.Referee;
import com.example.waterSports.repo.*;
import com.example.waterSports.utils.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/ref/")
public class RefereeController
{
    @Autowired
    OrderWaterSportRepo repoWs;
    @Autowired
    OrderParasalingRepo repoPs;
    @Autowired
    RefereeRepo repo;
    @Autowired
    ConfigRepo configRepo;
    @Autowired
    ActivityLogRepo repoActivityLog;

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
        System.out.println("referee = " + referee.toString());
        Referee refSaved = repo.save(referee);

        if(referee.getIdOwner() == -1)
        {
            refSaved.setIdOwner(refSaved.getId());
            repo.save(refSaved);
        }

        repoActivityLog.save(new ActivityLog("Refereee : New Record added. Referee = " + referee.getName() + ", Owner = " + repo.getReferenceById(referee.getIdOwner()).getName()));

        return "redirect:/ref/";
    }

    @GetMapping("/delete/{id}/")
    @ResponseBody
    public Integer delete(@PathVariable Long id)
    {
        Integer status = 0;

        if(repo.checkOwner(id) <= 1 && repoWs.countReferences(id) == 0 && repoPs.countReferences(id) == 0)
        {
            Referee referee = repo.getReferenceById(id);
            repoActivityLog.save(new ActivityLog("Refereee : Recorded Deleted. Referee = " + referee.getName() + ", Owner = " + repo.getReferenceById(referee.getIdOwner()).getName()));
            repo.deleteById(id);
            status = 1;
        }

        return status;
    }
}
