package kz.shokanov.rassulkhair.shop.repository;


import kz.shokanov.rassulkhair.shop.entity.Order;
import kz.shokanov.rassulkhair.shop.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepo extends JpaRepository<Order, Long> {
    List<Order> findAllByUser(User user);

    List<Order> findAllByOrderByStatusAsc();
}
