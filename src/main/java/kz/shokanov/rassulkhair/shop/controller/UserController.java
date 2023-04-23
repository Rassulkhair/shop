package kz.shokanov.rassulkhair.shop.controller;

import kz.shokanov.rassulkhair.shop.entity.User;
import kz.shokanov.rassulkhair.shop.repository.OrderRepo;
import kz.shokanov.rassulkhair.shop.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
@Controller
@AllArgsConstructor
public class UserController {
    private final UserService userService;
    private final OrderRepo orderRepo;

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

    @GetMapping("/user/page")
    public String userPage(Model model) {
        model.addAttribute("user1", userService.getCurrentUser());
        model.addAttribute("orders", orderRepo.findAllByUser(userService.getCurrentUser()));
        return "userPage";
    }
}