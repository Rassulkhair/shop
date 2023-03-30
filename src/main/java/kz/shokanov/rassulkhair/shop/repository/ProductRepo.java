package kz.shokanov.rassulkhair.shop.repository;

import kz.shokanov.rassulkhair.shop.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepo extends JpaRepository<Product, Long> {
}
