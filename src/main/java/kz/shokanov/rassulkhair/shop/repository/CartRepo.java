package kz.shokanov.rassulkhair.shop.repository;

import kz.shokanov.rassulkhair.shop.entity.Cart;
import kz.shokanov.rassulkhair.shop.entity.Product;
import kz.shokanov.rassulkhair.shop.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepo extends JpaRepository<Cart, Long> {
    List<Cart> findByUser(User user);
    Cart findCartByUserAndProduct(User user, Product product);
    Boolean existsCartByUserAndProduct(User user, Product product);
    Boolean existsCartByProduct(Product product);
}
