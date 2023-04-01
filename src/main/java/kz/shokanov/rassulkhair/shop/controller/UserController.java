package kz.shokanov.rassulkhair.shop.controller;

import kz.shokanov.rassulkhair.shop.Service.UserService;
import kz.shokanov.rassulkhair.shop.users.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
        userService.createUser(user.getUsername(), user.getPassword());
        return "redirect:/login";
    }
}
