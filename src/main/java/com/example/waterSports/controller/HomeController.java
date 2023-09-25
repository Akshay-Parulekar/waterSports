package com.example.waterSports.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController
{
    @GetMapping("/")
    public String welcome()
    {
        return "redirect:/activation/validate/";
    }
}
