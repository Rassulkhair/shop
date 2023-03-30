package kz.shokanov.rassulkhair.shop.controller;

import kz.shokanov.rassulkhair.shop.Service.UserService;
import kz.shokanov.rassulkhair.shop.users.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("user", new User());
        return "registration";
    }

    @PostMapping(value = "sign-up", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void signUp(@RequestBody UserModel userModel){
        userService.createUser(userModel.getUserName(), userModel.getPassword());
    }

    @Getter
    @Setter
    public static class UserModel {
        private String userName;
        private String password;
    }
}
