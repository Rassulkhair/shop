package kz.shokanov.rassulkhair.shop.service;

import kz.shokanov.rassulkhair.shop.entity.Product;
import kz.shokanov.rassulkhair.shop.entity.Review;
import kz.shokanov.rassulkhair.shop.entity.User;
import kz.shokanov.rassulkhair.shop.repository.ProductRepo;
import kz.shokanov.rassulkhair.shop.repository.ReviewRepo;
import kz.shokanov.rassulkhair.shop.repository.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ReviewService {

    private final ProductRepo productRepo;

    private final ReviewRepo reviewRepo;
    private UserRepo userRepo;
    private UserService userService;

    public double getAvgRating(long productId) {
        List<Review> reviews = productRepo.findById(productId).orElseThrow().getReviews();
        double avg = 0;
        if (!reviews.isEmpty()) {
            for (Review review : reviews) {
                avg = avg + review.getRating();
            }
            avg = avg / reviews.size();
        }
        return avg;
    }

    public boolean isReviewPresent(Product product) {
        User user = userService.getCurrentUser();
        Review review = reviewRepo.findByProductAndUser(product, user);
        return review == null;
    }

    public void publish(long id, int rating, String text) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            Long userId = userService.getCurrentUser().getId();
            Optional<User> user = userRepo.findById(userId);
            Optional<Product> product = productRepo.findById(id);
            Optional<Review> existingReview = Optional.ofNullable(reviewRepo.findByUserAndProduct(user, product));
            Review review;
            if (existingReview.isPresent()) {
                review = existingReview.get();
                review.setRating(rating);
                review.setText(text);
                review.set_published(true);
            } else {
                review = new Review();
                review.setUser(userRepo.findById(userId).orElseThrow());
                review.setProduct(productRepo.findById(id).orElseThrow());
                review.setRating(rating);
                review.setText(text);
            }
            review.setCreated_at(LocalDateTime.now());
            reviewRepo.save(review);
        }
    }
    public Product showDetails(Long id){
        return productRepo.findById(id).orElseThrow();
    }
}

