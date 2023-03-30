package kz.shokanov.rassulkhair.shop.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonAppend;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.util.List;

@Getter
@Setter

@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
@OneToMany( mappedBy = "category")
@JsonIgnore
    private List<Product> products;

    @OneToMany( mappedBy = "category")
    @JsonIgnore
    private List<Option> options;
}
