package kz.shokanov.rassulkhair.shop.controller;

import kz.shokanov.rassulkhair.shop.entity.Cart;
import kz.shokanov.rassulkhair.shop.service.CartService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/cart")
@AllArgsConstructor
public class CartController {
    private CartService cartService;

    @PostMapping("/{productId}")
    public String addToCart(@PathVariable(value = "productId") Long productId,
                            @RequestParam(value = "count") int count) {
        cartService.addCart(productId,count);
        return "redirect:/products";
    }


    @GetMapping()
    public String getAllCartItemsForUser(Model model) {
        List<Cart> cartItems = cartService.getAllCartItems();
        model.addAttribute("cartList",cartItems);
        model.addAttribute("summariseCOST",cartService.getCost(cartItems));
        return "cart";
    }

    @PostMapping("/delete/{cartItemId}")
    public String deleteCartItem(@PathVariable(value = "cartItemId") Long cartItemId) {
        cartService.deleteItemFromCart(cartItemId);
        return "redirect:/cart";
    }
}
