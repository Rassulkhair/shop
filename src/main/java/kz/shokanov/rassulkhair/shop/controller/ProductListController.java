package kz.shokanov.rassulkhair.shop.controller;

import kz.shokanov.rassulkhair.shop.entity.Category;
import kz.shokanov.rassulkhair.shop.entity.Product;
import kz.shokanov.rassulkhair.shop.repository.CategoryRepo;
import kz.shokanov.rassulkhair.shop.repository.OptionRepo;
import kz.shokanov.rassulkhair.shop.repository.ProductRepo;
import kz.shokanov.rassulkhair.shop.repository.UserRepo;
import kz.shokanov.rassulkhair.shop.service.ProductService;
import kz.shokanov.rassulkhair.shop.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@Controller
@AllArgsConstructor
public class ProductListController {
    private ProductRepo productRepo;
    private CategoryRepo categoryRepo;
    private OptionRepo optionRepo;

    ProductService productService;
    UserRepo userRepo;
    UserService userService;

    @GetMapping(path = "products")
    public String getProducts(@RequestParam(required = false) Long categoryId,
                              Model model) {
        List<Product> products = productService.getAllProducts(categoryId);
        model.addAttribute("avg", productService.getAvgCost(products));
        model.addAttribute("user", userService.getCurrentUser());
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
        model.addAttribute("product", productRepo.findById(id).orElseThrow());
        model.addAttribute("options", optionRepo.findAllByCategoryOrderById(product.getCategory()));
        return "update_product";
    }

    @PostMapping(path = "/products/edit/{productId}")
    public String saveUpdatedProduct(
            @PathVariable long productId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Double price,
            @RequestParam(required = false) List<String> updatedValues) {

       productService.updateProduct(productId, name, price, updatedValues);
        return "redirect:/products";
    }

    @GetMapping(path = "/products/delete/{id}")
    public String deleteProduct(
            @PathVariable("id") Long id) {
        productService.deleteProduct(id);
        return "redirect:/products";
    }
}

