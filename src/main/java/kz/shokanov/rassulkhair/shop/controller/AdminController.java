package kz.shokanov.rassulkhair.shop.controller;

import kz.shokanov.rassulkhair.shop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import kz.shokanov.rassulkhair.shop.entity.*;
import kz.shokanov.rassulkhair.shop.repository.*;

import java.util.List;

@Controller
@RequestMapping(path = "admin")
public class AdminController {
    @Autowired
    UserService userService;

    @GetMapping(path = "/admin/panel")
    public String getAdminPage(Model model) {
        User user = userService.getCurrentUser();

        return "adminPage";
    }
}
