package kz.shokanov.rassulkhair.shop.controller;

import kz.shokanov.rassulkhair.shop.entity.*;
import kz.shokanov.rassulkhair.shop.repository.OrderDetailsRepo;
import kz.shokanov.rassulkhair.shop.repository.OrderRepo;
import kz.shokanov.rassulkhair.shop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class UserController {
    @Autowired
    private final UserService userService;

    @Autowired
    private final OrderDetailsRepo orderDetailsRepo;

    @Autowired
    OrderRepo orderRepo;
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
    public String userPage(Model model){
        model.addAttribute("user1", userService.getCurrentUser());
        model.addAttribute("orders", orderRepo.findAllByUser(userService.getCurrentUser()));
        return "userPage";
    }

    @GetMapping("/order/{id}/details")
    public String showProductDetails(@PathVariable("id") Long id, Model model) {
        Order order = orderRepo.findById(id).orElseThrow();
        List<OrderDetails> orderDetails = orderDetailsRepo.findAllByOrder(order);
        model.addAttribute("orderDetails", orderDetails);
        return "orderDetails";
    }
}