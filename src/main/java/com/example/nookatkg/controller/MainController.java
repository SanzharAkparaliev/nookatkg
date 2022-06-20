package com.example.nookatkg.controller;


import com.example.nookatkg.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/")
    public String homePage(Model model) {
        model.addAttribute("title","Ноокат Таңы");
        model.addAttribute("categories",categoryService.getCategories());
        return "index";
    }
}
