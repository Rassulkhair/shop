package kz.shokanov.rassulkhair.shop.repository;

import kz.shokanov.rassulkhair.shop.entity.Category;
import kz.shokanov.rassulkhair.shop.entity.Option;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OptionRepo extends JpaRepository<Option, Long> {
    List<Option> findAllByCategoryOrderById(Category category);
}
