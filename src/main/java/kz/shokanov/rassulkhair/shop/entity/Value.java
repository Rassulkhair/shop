package kz.shokanov.rassulkhair.shop.entity;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
@Getter
@Setter

@Entity
@Table(name = "values")
public class Value {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String value;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "option_id")
    private Option option;
}
