package kz.shokanov.rassulkhair.shop.repository;

import kz.shokanov.rassulkhair.shop.entity.Option;
import kz.shokanov.rassulkhair.shop.entity.Product;
import kz.shokanov.rassulkhair.shop.entity.Value;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ValueRepo extends JpaRepository<Value, Long> {
    Value findByProductAndOption(Product product, Option option);
}
