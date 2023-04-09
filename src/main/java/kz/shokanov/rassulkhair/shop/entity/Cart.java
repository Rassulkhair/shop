package kz.shokanov.rassulkhair.shop.entity;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter

@Entity
@Table(name = "cart")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false, referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "FK_CART_PRODUCT"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Product product;

    private int count;

}