package com.ecommerdedemo.ecommercedemo.entities.concretes;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "historical_prices")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HistoricalPrice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Product product;

    private double price;

    private LocalDateTime purchaseDateTime; // price at the time of purchase
}
