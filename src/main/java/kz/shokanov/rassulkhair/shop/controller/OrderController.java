package kz.shokanov.rassulkhair.shop.controller;


import kz.shokanov.rassulkhair.shop.entity.Cart;
import kz.shokanov.rassulkhair.shop.entity.User;
import kz.shokanov.rassulkhair.shop.entity.enumiration.Status;
import kz.shokanov.rassulkhair.shop.repository.CartRepo;
import kz.shokanov.rassulkhair.shop.repository.OrderRepo;
import kz.shokanov.rassulkhair.shop.repository.UserRepo;
import kz.shokanov.rassulkhair.shop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/cart/order")
public class OrderController {

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private CartRepo cartRepo;

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepo userRepo;

    @GetMapping()
    public String orderPage(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
        Long userId = userRepo.findUserByName(userPrincipal.getUsername()).getId();
        User user = userRepo.findById(userId).orElseThrow();

        List<Cart> cartItems = cartRepo.findByUser(user);

        model.addAttribute("cartItems",cartItems);
        return "order-form";
    }


    @PostMapping("/create")
    public String createOrder(@ModelAttribute("order") kz.shokanov.rassulkhair.shop.entity.Order order,
                              @AuthenticationPrincipal User user,
                              @RequestParam("address") String address) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User currentUser = userRepo.findUserByName(userDetails.getUsername());
        order.setUser(currentUser);

        order.setStatus(Status.DELIEVERED);
        order.setAddress(address);
        order.setCreated_at(LocalDateTime.now());
        orderRepo.save(order);
        cartRepo.deleteAllByUser(currentUser);

        return "redirect:/order/" + order.getId();
    }
}
