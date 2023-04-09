package kz.shokanov.rassulkhair.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import kz.shokanov.rassulkhair.shop.entity.*;
import kz.shokanov.rassulkhair.shop.repository.*;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private CartRepo cartRepo;

    @PostMapping("/{productId}")
    public String addToCart(@PathVariable(value = "productId") Long productId,
                            @RequestParam(value = "count") int count) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
        Long userId = userRepo.findUserByName(userPrincipal.getUsername()).getId();
        User user = userRepo.findById(userId).orElseThrow();
        Product product = productRepo.findById(productId).orElseThrow();
        if (cartRepo.existsCartByUserAndProduct(user,product)){
            Cart c1 = cartRepo.findCartByUserAndProduct(user,product);
            cartRepo.delete(c1);
        }
        else {
            Cart cart = new Cart();
            cart.setUser(user);
            cart.setProduct(product);
            cart.setCount(count);
            cartRepo.save(cart);
        }
        return "redirect:/products";
    }


    @GetMapping()
    public String getAllCartItemsForUser(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
        Long userId = userRepo.findUserByName(userPrincipal.getUsername()).getId();
        User user = userRepo.findById(userId).orElseThrow();

        List<Cart> cartItems = cartRepo.findByUser(user);
        double cost = 0;
        for (var cart:cartItems
             ) {
            cost = cost + (cart.getCount()*cart.getProduct().getPrice());
        }
        model.addAttribute("cartList",cartItems);
        model.addAttribute("summariseCOST",cost);

        return "cart";
    }
    @PostMapping("/delete/{cartItemId}")
    public String deleteCartItem(@PathVariable(value = "cartItemId") Long cartItemId) {

        Cart cartItem = cartRepo.findById(cartItemId).orElseThrow();

        cartRepo.delete(cartItem);

        return "redirect:/cart";
    }
}
