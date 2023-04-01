package kz.shokanov.rassulkhair.shop.Service;

import jakarta.transaction.Transactional;
import kz.shokanov.rassulkhair.shop.repository.UserRepo;
import kz.shokanov.rassulkhair.shop.users.User;
import kz.shokanov.rassulkhair.shop.users.UserRole;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService{

    private final UserRepo userRepo;

    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void createUser(String username, String password){
        log.info("createUser() - start: userName={}", username);
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.addRole(UserRole.USER);
        userRepo.save(user);
    }
}
