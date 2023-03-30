package kz.shokanov.rassulkhair.shop.repository;

import kz.shokanov.rassulkhair.shop.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<Category, Long> {
}
