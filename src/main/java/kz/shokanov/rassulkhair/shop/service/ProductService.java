package kz.shokanov.rassulkhair.shop.service;

import kz.shokanov.rassulkhair.shop.entity.Category;
import kz.shokanov.rassulkhair.shop.entity.Option;
import kz.shokanov.rassulkhair.shop.entity.Product;
import kz.shokanov.rassulkhair.shop.entity.Value;
import kz.shokanov.rassulkhair.shop.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@AllArgsConstructor
public class ProductService {
   private CategoryRepo categoryRepo;
    private ProductRepo productRepo;
    private OptionRepo optionRepo;
    private ValueRepo valueRepo;

    public void addProduct(long categoryId, Product product, List<String> values) {
        Category category = categoryRepo.findById(categoryId).orElseThrow();
        List<Option> options = optionRepo.findAllByCategoryOrderById(category);
        product.setCategory(category);
        productRepo.save(product);

        for (int i = 0; i < options.size(); i++) {
            Value value = new Value();
            value.setProduct(product);
            value.setOption(options.get(i));
            value.setValue(values.get(i));
            valueRepo.save(value);
        }
    }

    public void updateProduct(long productId, String name, Double price, List<String> values) {
        Product product = productRepo.findById(productId).orElseThrow();
        if (name != null) product.setName(name);
        if (price != null) product.setPrice(price);
        productRepo.save(product);
        List<Option> optionList = optionRepo.findAllByCategoryOrderById(product.getCategory());
        for (int i = 0; i < optionList.size(); i++) {
            Value value = valueRepo.findByProductAndOption(product, optionList.get(i));
            if (value == null) {
                value = new Value();
                value.setProduct(product);
                value.setOption(optionList.get(i));
                value.setValue(values.get(i));
            } else if (!value.getValue().equals(values.get(i))) {
                value.setValue(values.get(i));
            }
            valueRepo.save(value);
        }
    }

    public String findValueByProductAndOption(Product product, Option option) {
        Value value = valueRepo.findByProductAndOption(product, option);
        return value != null ? value.getValue() : "";
    }

    public List<Product> getAllProducts(Long categoryId) {
        Sort sort = Sort.by(Sort.Order.desc("price"),
                Sort.Order.asc("id"));
        List<Product> products = productRepo.findAll(sort);
        if (categoryId != null) {
            Category category = categoryRepo.findById(categoryId).orElseThrow();
            products = category.getProducts();
        }
        return products;
    }

    public Integer getAvgCost(List<Product> products) {
        int avg = 0;
        if (!products.isEmpty()) {
            for (Product product : products) {
                avg = (int) (avg + product.getPrice());
            }
            avg = avg / products.size();
        }
    return avg;
    }

    public void deleteProduct(Long id){
        Product product = productRepo.findById(id).orElseThrow();
        valueRepo.deleteAll(product.getValues());
        productRepo.delete(product);
    }
}

