package kz.shokanov.rassulkhair.shop.controller;

import kz.shokanov.rassulkhair.shop.service.UserService;
import kz.shokanov.rassulkhair.shop.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;

@Controller
@RequiredArgsConstructor
public class UserController {
    @Autowired
    private final UserService userService;
    @GetMapping("/registration")
    public String registerForm(Model model) {
        model.addAttribute("user", new User());
        return "registration";
    }

    @PostMapping("/registration")
    public String saveNewUser(@Validated @ModelAttribute("user") User user) {
        userService.createUser(user.getLogin(), user.getPassword(), user.getName(), user.getLastname(), LocalDateTime.now());
        return "redirect:/login";
    }
}