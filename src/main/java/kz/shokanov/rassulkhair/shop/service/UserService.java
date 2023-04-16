package kz.shokanov.rassulkhair.shop.service;

import jakarta.transaction.Transactional;
import kz.shokanov.rassulkhair.shop.entity.Role;
import kz.shokanov.rassulkhair.shop.repository.RoleRepo;
import kz.shokanov.rassulkhair.shop.repository.UserRepo;
import kz.shokanov.rassulkhair.shop.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService{

    private final UserRepo userRepo;

    private final PasswordEncoder passwordEncoder;
    private final RoleRepo roleRepo;

    @Transactional
    public void createUser(String name, String password , String login , String lastname , LocalDateTime createdAt){
        log.info("createUser() - start: userName={}", login);
        User user = new User();
        user.setName(name);
        user.setLastname(lastname);
        user.setLogin(login);
        user.setCreatedAt(createdAt);
        user.setPassword(passwordEncoder.encode(password));
        user.setRoles(Collections.singleton(new Role(3L,"ROLE_USER")));
        userRepo.save(user);
    }

    public User getCurrentUser() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        return userRepo.findUserByName(authentication.getName());
    }
}
