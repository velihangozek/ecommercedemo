package com.ecommerdedemo.ecommercedemo.entities.concretes;

import com.ecommerdedemo.ecommercedemo.entities.abstracts.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "carts")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "total_price")
    private double totalPrice;


    // The cart has a relationship with the Customer.
//    @OneToOne
//    @JoinColumn(name = "customer_id")
//    private Customer customer;

    @OneToOne(mappedBy = "cart")
    private Customer customer;

    @OneToMany(mappedBy = "cart")
    private List<ProductCart> productCarts; // We can have -> List of Products in the Cart object


    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> items = new ArrayList<>();

}
