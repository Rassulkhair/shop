package kz.shokanov.rassulkhair.shop.repository;

import kz.shokanov.rassulkhair.shop.entity.Order;
import kz.shokanov.rassulkhair.shop.entity.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDetailsRepo extends JpaRepository<OrderDetails , Long> {
    List<OrderDetails> findAllByOrder(Order order);
}
