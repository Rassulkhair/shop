package kz.shokanov.rassulkhair.shop.Service;

import kz.shokanov.rassulkhair.shop.entity.Category;
import kz.shokanov.rassulkhair.shop.entity.Option;
import kz.shokanov.rassulkhair.shop.entity.Product;
import kz.shokanov.rassulkhair.shop.entity.Value;
import kz.shokanov.rassulkhair.shop.repository.CategoryRepo;
import kz.shokanov.rassulkhair.shop.repository.OptionRepo;
import kz.shokanov.rassulkhair.shop.repository.ProductRepo;
import kz.shokanov.rassulkhair.shop.repository.ValueRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    CategoryRepo categoryRepo;
    @Autowired
    ProductRepo productRepo;
    @Autowired
    OptionRepo optionRepo;
    @Autowired
    ValueRepo valueRepo;

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
        System.out.println("---------------");
        System.out.println(values);
        System.out.println("---------------");
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
}

