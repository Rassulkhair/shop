package kz.shokanov.rassulkhair.shop.controller;

import kz.shokanov.rassulkhair.shop.entity.Review;
import kz.shokanov.rassulkhair.shop.service.ReviewService;
import kz.shokanov.rassulkhair.shop.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@AllArgsConstructor
public class ReviewController {
    ReviewService reviewService;
    UserService userService;

    @GetMapping("/products/{id}/details")
    public String showProductDetails(@PathVariable("id") Long id, Model model) {
        model.addAttribute("reviews", new Review());
        model.addAttribute("product", reviewService.showDetails(id));
        model.addAttribute("user", userService.getCurrentUser());
        return "productDetails";
    }

    @PostMapping(path = "/products/{id}/publish_review")
    public String publishReview(
            @PathVariable("id") Long id,
            @RequestParam(name = "rating") int rating,
            @RequestParam(name = "text") String text) {
        reviewService.publish(id, rating, text);
        return String.format("redirect:/products/%d/details", id);
    }
}
