package kz.shokanov.rassulkhair.shop.service;

import jakarta.transaction.Transactional;
import kz.shokanov.rassulkhair.shop.entity.enumiration.Role;
import kz.shokanov.rassulkhair.shop.repository.UserRepo;
import kz.shokanov.rassulkhair.shop.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService{

    private final UserRepo userRepo;

    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void createUser(String name, String password , String login , String lastname , LocalDateTime createdAt){
        log.info("createUser() - start: userName={}", login);
        User user = new User();
        user.setName(name);
        user.setLastname(lastname);
        user.setLogin(login);
        user.setCreatedAt(createdAt);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(Role.USER);
        userRepo.save(user);
    }
}
