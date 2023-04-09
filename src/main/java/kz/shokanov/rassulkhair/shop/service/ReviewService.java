package kz.shokanov.rassulkhair.shop.service;

import kz.shokanov.rassulkhair.shop.entity.Product;
import kz.shokanov.rassulkhair.shop.entity.Review;
import kz.shokanov.rassulkhair.shop.entity.User;
import kz.shokanov.rassulkhair.shop.repository.ProductRepo;
import kz.shokanov.rassulkhair.shop.repository.ReviewRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

@Service
public class ReviewService {

    private final ProductRepo productRepo;

    private final ReviewRepo reviewRepo;

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
}

