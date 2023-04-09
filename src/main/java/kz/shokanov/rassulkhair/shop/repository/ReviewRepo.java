package kz.shokanov.rassulkhair.shop.repository;

import kz.shokanov.rassulkhair.shop.entity.Product;
import kz.shokanov.rassulkhair.shop.entity.Review;
import kz.shokanov.rassulkhair.shop.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepo extends JpaRepository<Review, Long> {
    Review findByUserAndProduct(Optional<User> user, Optional<Product> product);

    List<Review> findAllById(Review review);
    Review findByProductAndUser(Product product, User user);

    List<Review> findByProduct_IdAndUser_Id(Long productId, Long userId);
}
