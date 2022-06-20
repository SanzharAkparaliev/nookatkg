package com.example.nookatkg.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @GetMapping("/")
    public String AdminPage(Model model){
        model.addAttribute("title","Админ панели");
        return "admin/admin-dashboard";
    }
}