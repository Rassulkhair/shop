package kz.shokanov.rassulkhair.shop.service;

import kz.shokanov.rassulkhair.shop.entity.Product;
import kz.shokanov.rassulkhair.shop.entity.Review;
import kz.shokanov.rassulkhair.shop.entity.User;
import kz.shokanov.rassulkhair.shop.repository.ProductRepo;
import kz.shokanov.rassulkhair.shop.repository.ReviewRepo;
import kz.shokanov.rassulkhair.shop.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
public class ReviewService {

    private final ProductRepo productRepo;

    private final ReviewRepo reviewRepo;
    private UserRepo userRepo;
    @Autowired
    private UserService userService;

    public ReviewService(ProductRepo productRepo, ReviewRepo reviewRepo) {
        this.productRepo = productRepo;
        this.reviewRepo = reviewRepo;
    }

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

}

