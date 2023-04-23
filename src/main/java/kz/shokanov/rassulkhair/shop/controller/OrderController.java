package kz.shokanov.rassulkhair.shop.controller;


import kz.shokanov.rassulkhair.shop.entity.Order;
import kz.shokanov.rassulkhair.shop.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@AllArgsConstructor
public class OrderController {
    private OrderService orderService;

    @GetMapping("/cart/order")
    public String orderPage(Model model) {
        model.addAttribute("cartItems", orderService.getOrderPage());
        return "order-form";
    }


    @PostMapping("/cart/order/create")
    public String createOrder(@ModelAttribute("order") Order order,
                              @RequestParam("address") String address) {
        orderService.createOrder(order, address);

        return "redirect:/products";
    }

    @GetMapping("/order/{id}/details")
    public String showProductDetails(@PathVariable("id") Long id, Model model) {
        model.addAttribute("orderDetails", orderService.getAllDetails(orderService.getOrderDetails(id)));
        return "orderDetails";
    }
}
