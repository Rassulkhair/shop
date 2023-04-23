package kz.shokanov.rassulkhair.shop.service;

import kz.shokanov.rassulkhair.shop.entity.Cart;
import kz.shokanov.rassulkhair.shop.entity.Order;
import kz.shokanov.rassulkhair.shop.entity.OrderDetails;
import kz.shokanov.rassulkhair.shop.entity.User;
import kz.shokanov.rassulkhair.shop.entity.enumiration.Status;
import kz.shokanov.rassulkhair.shop.repository.CartRepo;
import kz.shokanov.rassulkhair.shop.repository.OrderDetailsRepo;
import kz.shokanov.rassulkhair.shop.repository.OrderRepo;
import kz.shokanov.rassulkhair.shop.repository.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class OrderService {

    private OrderRepo orderRepo;

    private CartRepo cartRepo;

    private UserService userService;
    private UserRepo userRepo;

    private OrderDetailsRepo orderDetailsRepo;

    public void createOrder(Order order, String address) {
        Long userId = userService.getCurrentUser().getId();
        User user = userRepo.findById(userId).orElseThrow();
        order.setUser(user);

        order.setStatus(Status.INSTOCK);
        order.setAddress(address);
        order.setCreated_at(LocalDateTime.now());
        orderRepo.save(order);
        List<Cart> carts = cartRepo.findByUser(user);
        for (var cart : carts
        ) {
            OrderDetails orderDetails = new OrderDetails();
            orderDetails.setOrder(order);
            orderDetails.setCount(cart.getCount());
            orderDetails.setProduct(cart.getProduct());
            orderDetailsRepo.save(orderDetails);
        }
        cartRepo.deleteAllByUser(user);
    }

    public List<Order> getOrderPage() {
        return orderRepo.findAllByUser(userRepo.findById(userService.getCurrentUser().getId()).orElseThrow());
    }

    public List<Order> getAllOrdersByStatus(){
        return orderRepo.findAllByOrderByStatusAsc();
    }

    public Order getOrderDetails(Long id){
        return orderRepo.findById(id).orElseThrow();
    }

    public List<OrderDetails> getAllDetails(Order order){
        return orderDetailsRepo.findAllByOrder(order);
    }

    public void updateStatusOrder(Long orderId , Status status){
        Order order = orderRepo.findById(orderId).orElseThrow();
        order.setStatus(status);
        orderRepo.save(order);
    }
}
