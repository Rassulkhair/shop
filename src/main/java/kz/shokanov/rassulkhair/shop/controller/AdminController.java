package kz.shokanov.rassulkhair.shop.controller;

import kz.shokanov.rassulkhair.shop.entity.enumiration.Status;
import kz.shokanov.rassulkhair.shop.service.OrderService;
import kz.shokanov.rassulkhair.shop.service.ReviewService;
import kz.shokanov.rassulkhair.shop.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = "admin")
@AllArgsConstructor
public class AdminController {
    UserService userService;
    OrderService orderService;
    ReviewService reviewService;

    @GetMapping(path = "/panel")
    public String getAdminPage(Model model, @RequestParam(required = false) Long categoryId) {
        model.addAttribute("orders", orderService.getAllOrdersByStatus());
        model.addAttribute("user", userService.getCurrentUser());
        model.addAttribute("categoryId", categoryId);
        return "adminPage";
    }

    @PostMapping(path = "/updateOrderStatus")
    public String updateOrderStatus(@RequestParam Long orderId, @RequestParam Status status) {
        orderService.updateStatusOrder(orderId, status);
        return "redirect:/admin/panel";
    }

    @GetMapping(path = "/panel/reviews")
    public String getReviews(Model model) {
        model.addAttribute("reviewList", reviewService.reviewList());
        return "reviewPanel";
    }

    @PostMapping(path = "/delete/review/{id}")
    public String deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
        return "redirect:/admin/panel/reviews";
    }
    @GetMapping(path = "/reviews/post")
    public String postReview(
            @RequestParam(name = "reviewId") long id
    ) {
        reviewService.postReview(id);
        return "redirect:/admin/panel/reviews";
    }
}
