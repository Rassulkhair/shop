package kz.shokanov.rassulkhair.shop.entity;

import jakarta.persistence.*;
import kz.shokanov.rassulkhair.shop.entity.enumiration.Role;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Setter
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(value = EnumType.ORDINAL)
    private Role role;

    private String login;
    private String password;
    private String name;
    private String lastname;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "user")
    List<Order> orders;

    @OneToMany(mappedBy = "user")
    List<Review> reviews;

}
