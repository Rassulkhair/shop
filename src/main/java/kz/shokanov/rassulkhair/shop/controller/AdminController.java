package kz.shokanov.rassulkhair.shop.controller;

import kz.shokanov.rassulkhair.shop.entity.enumiration.Status;
import kz.shokanov.rassulkhair.shop.service.OrderService;
import kz.shokanov.rassulkhair.shop.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(path = "admin")
@AllArgsConstructor
public class AdminController {
    UserService userService;
    OrderService orderService;

    @GetMapping(path = "/panel")
    public String getAdminPage(Model model, @RequestParam(required = false) Long categoryId) {
        model.addAttribute("orders", orderService.getAllOrdersByStatus());
        model.addAttribute("user", userService.getCurrentUser());
        model.addAttribute("categoryId", categoryId);
        return "adminPage";
    }

    @PostMapping(path = "/updateOrderStatus")
    public String updateOrderStatus(@RequestParam Long orderId, @RequestParam Status status) {
        orderService.updateStatusOrder(orderId,status);
        return "redirect:/admin/panel";
    }


}
