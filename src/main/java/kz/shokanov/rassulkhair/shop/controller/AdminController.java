package kz.shokanov.rassulkhair.shop.controller;

import kz.shokanov.rassulkhair.shop.entity.enumiration.Status;
import kz.shokanov.rassulkhair.shop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import kz.shokanov.rassulkhair.shop.entity.*;
import kz.shokanov.rassulkhair.shop.repository.*;

import java.util.List;

@Controller
@RequestMapping(path = "admin")
public class AdminController {
    @Autowired
    UserService userService;
    @Autowired
    UserRepo userRepo;
    @Autowired
    OrderRepo orderRepo;
    @Autowired
    CategoryRepo categoryRepo;

    @GetMapping(path = "/panel")
    public String getAdminPage(Model model, @RequestParam(required = false) Long categoryId) {
        Long userId = userService.getCurrentUser().getId();
        User user = userRepo.findById(userId).orElseThrow();
        List<Order> orders = orderRepo.findAllByOrderByStatusAsc(); // Здесь предполагается, что у вас есть метод findAllByOrderByStatusAsc() в вашем репозитории для сортировки по статусу
        model.addAttribute("orders", orders);
        model.addAttribute("user", user);
        model.addAttribute("categoryId", categoryId);
        return "adminPage";
    }

    @PostMapping(path = "/updateOrderStatus")
    public String updateOrderStatus(@RequestParam Long orderId, @RequestParam Status status) {
        Order order = orderRepo.findById(orderId).orElseThrow();
        order.setStatus(status);
        orderRepo.save(order);
        return "redirect:/admin/panel";
    }


}
