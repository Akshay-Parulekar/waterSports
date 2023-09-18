package com.example.waterSports.controller;

import com.example.waterSports.modal.Config;
import com.example.waterSports.repo.ConfigRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/config/")
public class ConfigController
{
    @Autowired
    ConfigRepo repo;

    @PostMapping("/info/")
    public String saveConfig(Model model, String title, String header, String footer, String contact, String address)
    {
        repo.save(new Config("title", title));
        repo.save(new Config("header", header));
        repo.save(new Config("footer", footer));
        repo.save(new Config("contact", contact));
        repo.save(new Config("address", address));

        model.addAttribute("msg", "Data saved successfully!");

        return "redirect:/water/";
    }

    @PostMapping("/login/")
    public String saveLogin(Model model, String usernameCur, String usernameNew, String passwordCur, String passwordNew)
    {
        String usernameCurDb = repo.findOneByProp("username").getVal();
        String passwordCurDb = repo.findOneByProp("password").getVal();

        if(usernameCurDb.equals(usernameCur) && passwordCurDb.equals(passwordCur))
        {
            repo.save(new Config("username", usernameNew));
            repo.save(new Config("password", passwordNew));
            model.addAttribute("msg", "Data saved successfully!");
        }
        else
        {
            model.addAttribute("msg", "Invalid Username or Password!");
        }

        return "redirect:/water/";
    }
}
