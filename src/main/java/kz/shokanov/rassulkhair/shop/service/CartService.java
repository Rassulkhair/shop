package kz.shokanov.rassulkhair.shop.service;

import kz.shokanov.rassulkhair.shop.entity.Cart;
import kz.shokanov.rassulkhair.shop.entity.Product;
import kz.shokanov.rassulkhair.shop.entity.User;
import kz.shokanov.rassulkhair.shop.repository.CartRepo;
import kz.shokanov.rassulkhair.shop.repository.ProductRepo;
import kz.shokanov.rassulkhair.shop.repository.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CartService {
    private UserRepo userRepo;
    private ProductRepo productRepo;
    private CartRepo cartRepo;
    private UserService userService;


    public void addCart(long productId, int count) {
        Long userId = userService.getCurrentUser().getId();
        User user = userRepo.findById(userId).orElseThrow();
        Product product = productRepo.findById(productId).orElseThrow();
        if (cartRepo.existsCartByUserAndProduct(user, product)) {
            Cart c1 = cartRepo.findCartByUserAndProduct(user, product);
            cartRepo.delete(c1);
        } else {
            Cart cart = new Cart();
            cart.setUser(user);
            cart.setProduct(product);
            cart.setCount(count);
            cartRepo.save(cart);
        }
    }

    public void deleteItemFromCart(long cartItemId) {
        cartRepo.delete(cartRepo.findById(cartItemId).orElseThrow());
    }

    public List<Cart> getAllCartItems() {
        Long userId = userService.getCurrentUser().getId();
        User user = userRepo.findById(userId).orElseThrow();
        return cartRepo.findByUser(user);
    }

    public Double getCost(List<Cart> cartItems) {
        double cost = 0;
        for (var cart : cartItems
        ) {
            cost = cost + (cart.getCount() * cart.getProduct().getPrice());
        }

        return cost;
    }
}
