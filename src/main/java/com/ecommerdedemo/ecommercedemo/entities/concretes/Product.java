package com.ecommerdedemo.ecommercedemo.entities.concretes;

import com.ecommerdedemo.ecommercedemo.entities.abstracts.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Entity
@Table(name = "products")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;
    @Column(name = "price")
    private double price;

    // Calculate current price based on discount rate
    @Transient
    public double getCurrentPrice() {
        return price * (1 - discountRate);
    }

    @Column(name = "stock")
    private int stock;

    @Column(name = "discount_rate")
    private double discountRate;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "product")
    private List<ProductCart> productCarts;

}
