package kz.shokanov.rassulkhair.shop.controller;

import kz.shokanov.rassulkhair.shop.entity.User;
import kz.shokanov.rassulkhair.shop.repository.*;
import kz.shokanov.rassulkhair.shop.service.ProductService;
import kz.shokanov.rassulkhair.shop.entity.Category;
import kz.shokanov.rassulkhair.shop.entity.Option;
import kz.shokanov.rassulkhair.shop.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@Controller
public class ProductListController {
    @Autowired
    private ProductRepo productRepo;
    @Autowired
    private CategoryRepo categoryRepo;
    @Autowired
    private OptionRepo optionRepo;
    @Autowired
    private ValueRepo valueRepo;

    @Autowired
    ProductService productService;
    @Autowired
    UserRepo userRepo;

    @GetMapping(path = "products")
    public String getProducts(@RequestParam(required = false) Long categoryId,
                              Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
        Long userId = userRepo.findUserByName(userPrincipal.getUsername()).getId();
        User user = userRepo.findById(userId).orElseThrow();
        Sort sort = Sort.by(Sort.Order.desc("price"),
                Sort.Order.asc("id"));
        List<Product> products = productRepo.findAll(sort);
        if (categoryId != null) {
            Category category = categoryRepo.findById(categoryId).orElseThrow();
            model.addAttribute("category_name", category.getName());
            products = category.getProducts();
        }
        int avg = 0;
        if (!products.isEmpty()) {
            for (Product product : products) {
                avg = (int) (avg + product.getPrice());
            }
            avg = avg / products.size();
        }
        model.addAttribute("avg", avg);
        model.addAttribute("user", user);
        model.addAttribute("products", products);
        model.addAttribute("categoryId", categoryId);
        return "productList";
    }

    @GetMapping(path = "/products/add")
    public String createProduct(@RequestParam(required = false) Long categoryId,
                                Model model) {
        if (categoryId != null) {
            Category category = categoryRepo.findById(categoryId).orElseThrow();
            List<String> optionValues = new ArrayList<>();
            for (int i = 0; i < category.getOptions().size(); i++) {
                optionValues.add("");
            }
            model.addAttribute("optionValues", optionValues);
            model.addAttribute("options", optionRepo.findAllByCategoryOrderById(category));
            model.addAttribute("category", category);
        } else {
            model.addAttribute("categories", categoryRepo.findAll());
        }
        model.addAttribute("categoryId", categoryId);
        model.addAttribute("product", new Product());
        return "add_product";
    }

    @PostMapping(path = "/products/add")
    public String addProduct(@ModelAttribute Product product,
                             @RequestParam Long categoryId,
                             @RequestParam List<String> optionValues,
                             Model model) {
        productService.addProduct(categoryId, product, optionValues);
        model.addAttribute("message", "Product added successfully");
        return "redirect:/products";
    }
    @GetMapping(path = "/products/edit/{id}")
    public String updateProduct(
            @PathVariable("id") Long id,
            Model model) {

        Product product = productRepo.findById(id).orElseThrow();
        List<Option> options = optionRepo.findAllByCategoryOrderById(product.getCategory());
        model.addAttribute("product", product);
        model.addAttribute("options", options);
        return "update_product";
    }

    @PostMapping(path = "/products/edit/{id}")
    public String saveUpdatedProduct(
            @RequestParam long productId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Double price,
            @RequestParam(required = false) List<String> updatedValues) {

       productService.updateProduct(productId, name, price, updatedValues);
        return "redirect:/products";
    }

    @GetMapping(path = "/products/delete/{id}")
    public String deleteProduct(
            @PathVariable("id") Long id
    ) {
        Product product = productRepo.findById(id).orElseThrow();
        valueRepo.deleteAll(product.getValues());
        productRepo.delete(product);
        return "redirect:/products";
    }
}

