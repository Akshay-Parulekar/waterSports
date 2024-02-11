package com.example.waterSports.controller;

import com.example.waterSports.modal.Config;
import com.example.waterSports.repo.ConfigRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/config/")
public class ConfigController
{
    @Autowired
    ConfigRepo repo;

    @PostMapping("/info/")
    @ResponseBody
    public Integer saveConfig(Model model, String title, String header, String footer, String contact, String address, String printer, String receiptWidth)
    {
        int status = 0;

        repo.save(new Config("title", title));
        repo.save(new Config("header", header));
        repo.save(new Config("footer", footer));
        repo.save(new Config("contact", contact));
        repo.save(new Config("address", address));
        repo.save(new Config("printer", printer));
        repo.save(new Config("receiptWidth", receiptWidth));

        status = 1;

        return status;
    }

    @PostMapping("/login/")
    @ResponseBody
    public Integer saveLogin(Model model, String usernameCur, String usernameNew, String passwordCur, String passwordNew)
    {
        int status = 0;

        String usernameCurDb = repo.findOneByProp("username").getVal();
        String passwordCurDb = repo.findOneByProp("password").getVal();

        if(usernameCurDb.equals(usernameCur) && passwordCurDb.equals(passwordCur))
        {
            repo.save(new Config("username", usernameNew));
            repo.save(new Config("password", passwordNew));
            status = 1;
        }

        return status;
    }

    @GetMapping("/adminpassword/")
    @ResponseBody
    public String getAdminPasswordd()
    {
        return repo.findOneByProp("adminpassword").getVal();
    }

    @PostMapping("/adminpassword/")
    @ResponseBody
    public Integer saveAdminPassword(Model model, String passwordCur, String passwordNew)
    {
        int status = 0;

        String passwordCurDb = repo.findOneByProp("adminpassword").getVal();

        if(passwordCurDb.equals(passwordCur))
        {
            repo.save(new Config("adminpassword", passwordNew));
            status = 1;
        }

        return status;
    }
}
