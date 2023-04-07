package kz.shokanov.rassulkhair.shop.controller;

import kz.shokanov.rassulkhair.shop.entity.Product;
import kz.shokanov.rassulkhair.shop.entity.Review;
import kz.shokanov.rassulkhair.shop.entity.User;
import kz.shokanov.rassulkhair.shop.repository.ProductRepo;
import kz.shokanov.rassulkhair.shop.repository.ReviewRepo;
import kz.shokanov.rassulkhair.shop.repository.UserRepo;
import kz.shokanov.rassulkhair.shop.service.ProductService;
import kz.shokanov.rassulkhair.shop.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
public class ReviewController {
    @Autowired
    ReviewRepo reviewRepo;
    @Autowired
    UserRepo userRepo;
    @Autowired
    ProductRepo productRepo;
    @Autowired
    ProductService productService;
    @Autowired
    ReviewService reviewService;

    @GetMapping("/products/{id}/details")
    public String showProductDetails(@PathVariable("id") Long id, Model model) {
        Product product = productRepo.findById(id).orElseThrow();
        model.addAttribute("reviews", new Review());
        model.addAttribute("product", product);
        return "productDetails";
    }


    @PostMapping(path = "/products/{id}/publish_review")
    public String publishReview(
            @PathVariable("id") Long id,
            @RequestParam(name = "rating") int rating,
            @RequestParam(name = "text") String text
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId ;
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
            userId = userRepo.findUserByName(userPrincipal.getUsername()).getId();
            Optional<User> user = userRepo.findById(userId);
            Optional<Product> product = productRepo.findById(id);
            Optional<Review> existingReview = Optional.ofNullable(reviewRepo.findByUserAndProduct(user, product));
            if (existingReview.isPresent()) {
                Review review = existingReview.get();
                review.setRating(rating);
                review.setText(text);
                review.set_published(true);
                review.setCreated_at(LocalDateTime.now());
                reviewRepo.save(review);
            } else {
                Review review = new Review();
                review.setUser(userRepo.findById(userId).orElseThrow());
                review.setProduct(productRepo.findById(id).orElseThrow());
                review.setRating(rating);
                review.setText(text);
                review.setCreated_at(LocalDateTime.now());
                reviewRepo.save(review);
            }
        }
        return String.format("redirect:/products/%d/details", id);
    }
}
