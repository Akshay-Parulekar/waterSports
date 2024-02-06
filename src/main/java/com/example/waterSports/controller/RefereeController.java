package com.example.waterSports.controller;

import com.example.waterSports.modal.Referee;
import com.example.waterSports.repo.RefereeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/ref/")
public class RefereeController
{
    @Autowired
    RefereeRepo repo;

    @GetMapping("/")
    public String home(Model model)
    {
        List<Referee> list = repo.findAll();
        model.addAttribute("list", list);
        return "referee";
    }

    @PostMapping("/")
    public String add(Referee referee)
    {
        repo.save(referee);
        return "redirect:/ref/";
    }

    @GetMapping("/delete/{id}/")
    public String delete(Long id)
    {
        repo.deleteById(id);
        return "redirect:/ref/";
    }
}
