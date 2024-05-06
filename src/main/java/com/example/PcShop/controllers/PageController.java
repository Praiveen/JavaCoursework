package com.example.PcShop.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {


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

    @GetMapping("/news_page1")
    public String news_page1(){
        return "news_page1";
    }

    @GetMapping("/news_page2")
    public String news_page2(){
        return "news_page2";
    }

    @GetMapping("/news_page3")
    public String news_page3(){
        return "news_page3";
    }

    @GetMapping("/news_page4")
    public String news_page4(){
        return "news_page4";
    }

    @GetMapping("/news_page5")
    public String news_page5(){
        return "news_page5";
    }

    @GetMapping("/news_page6")
    public String news_page6(){
        return "news_page6";
    }

    @GetMapping("/news_page7")
    public String news_page7(){
        return "news_page7";
    }


}

