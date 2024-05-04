package com.example.PcShop.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @GetMapping("/index")
    public String index1() {
        return "index";
    }

    @GetMapping("/index.html")
    public String index2() {
        return "index";
    }

//    @GetMapping("/user_page")
//    public String userPage1(){
//        return "user_page";
//    }

    @GetMapping("/about")
    public String getAbouts(){
        return "about";
    }

    @GetMapping("/pc_recomendation")
    public String getPcRecomend(){
        return "pc_recomendation";
    }



}

