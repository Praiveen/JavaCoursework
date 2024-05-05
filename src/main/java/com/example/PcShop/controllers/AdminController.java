package com.example.PcShop.controllers;

import com.example.PcShop.entities.User;
import com.example.PcShop.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class AdminController {
    @Autowired
    private UserService userService;

    @GetMapping("/admin")
    public String adminPage(Model model){
        model.addAttribute("users", userService.findAll());
        return "admin";
    }


    @PostMapping("/admin/delete")
    public String deleteUser(@RequestParam("userId") Long userId, RedirectAttributes redirectAttributes) {
        userService.deleteUser(userId);
        redirectAttributes.addFlashAttribute("successMessage", "User deleted successfully");
        return "redirect:/admin";
    }

    @PostMapping("/admin/bans")
    public String bans(@RequestParam("userId") Long userId, RedirectAttributes redirectAttributes) {
        userService.updateUserBanStatus(userId);
        return "redirect:/admin";
    }

    @PostMapping("/userDetailsView")
    public String bans(@RequestParam("userId") Long userId, RedirectAttributes redirectAttributes, Model model) {
        User user = userService.findById(userId);
        model.addAttribute("users", userService.findAll());
        model.addAttribute("details", user.getOrders());
        return "admin";
    }

}
