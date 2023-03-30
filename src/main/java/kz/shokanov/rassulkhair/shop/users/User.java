package kz.shokanov.rassulkhair.shop.users;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    @OneToMany(mappedBy = "user", cascade = {CascadeType.ALL})
    private List<Role> roles;

    public void addRole(UserRole userRole) {
        if (roles == null) {
            roles = new ArrayList<>();
        }
        Role role = new Role();
        role.setRole(userRole);
        role.setUser(this);
        roles.add(role);
    }
}
