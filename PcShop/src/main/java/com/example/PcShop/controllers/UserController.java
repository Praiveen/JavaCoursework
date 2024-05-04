package com.example.PcShop.controllers;

import com.example.PcShop.entities.Role;
import com.example.PcShop.entities.User;
import com.example.PcShop.services.CustomUserDetailsService;
import com.example.PcShop.services.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

import org.springframework.ui.Model;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/register")
    public String registration(Model model) {
        model.addAttribute("userReg", new User());

        return "register";
    }


//    @PostMapping("/register/save")
//    public RedirectView addNewUser(@RequestParam String email,
//                             @RequestParam String user_name,
//                             @RequestParam String last_name,
//                             @RequestParam String password) {
//        User user = new User();
//        user.setEmail(email);
//        user.setFirstName(user_name);
//        user.setLastName(last_name);
//        user.setPassword(password);
//        System.out.println(user.getEmail());
//        System.out.println(user.getFirstName());
//        userService.saveUser(user);
//        RedirectView redirectView = new RedirectView();
//        redirectView.setUrl("/index.html");
//        return redirectView;
//    }


    @PostMapping(value = "/regSave", consumes = "application/x-www-form-urlencoded")
    public String addNewUser(@ModelAttribute("userReg") @Valid User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "register";
        }
        if (!user.getPassword().equals(user.getPasswordConfirm())) {
            model.addAttribute("Message", "Пароли не совпадают");
            System.out.println("pass");
            return "register";
        }
        if (user.getPassword().length() < 5) {
            model.addAttribute("Message", "Пароль должен содержать минимум 5 символов");
            return "register";
        }
        user.setAccountBan(false);
        if (!userService.saveUser(user, "new")) {
            model.addAttribute("Message", "Пользователь с такой почтой уже зарегестрирован");
            return "register";
        }

        model.addAttribute("RegFull", "Аккаунт зарегестрирован, теперь можно в него войти!");
        return "register";
    }

    @GetMapping("/user_page")
    public String userPage(@AuthenticationPrincipal CustomUserDetailsService.CustomUserDetails customUserDetails,
                            Model model){
        Set<Role> roles = userService.findById(customUserDetails.getUser().getId()).getRoles();
        List<String> roleNames = new ArrayList<>();

        for (Role role : roles) {
            roleNames.add(role.getName());
        }
        model.addAttribute("role", roleNames);
        model.addAttribute("user", userService.findById(customUserDetails.getUser().getId()));
        model.addAttribute("userOrders", userService.findById(customUserDetails.getUser().getId()).getOrders());
        return "user_page";
    }

    @PostMapping("setNewUserDetails")
    public String setNewUserDetails(@AuthenticationPrincipal CustomUserDetailsService.CustomUserDetails customUserDetails,
                                    @ModelAttribute("user") @Valid User userDetail,
                                    Model model){
        User user = userService.findById(customUserDetails.getUser().getId());
        user.setUsername(userDetail.getUsername());
        user.setName(userDetail.getName());
        user.setLastName(userDetail.getLastName());
        user.setPhoneNumber(userDetail.getPhoneNumber());
        userService.saveUser(user, "update");


        Set<Role> roles = userService.findById(customUserDetails.getUser().getId()).getRoles();
        List<String> roleNames = new ArrayList<>();

        for (Role role : roles) {
            roleNames.add(role.getName());
        }
        model.addAttribute("role", roleNames);
        model.addAttribute("user", userService.findById(customUserDetails.getUser().getId()));
        model.addAttribute("userOrders", userService.findById(customUserDetails.getUser().getId()).getOrders());

        return "user_page";
    }

}
