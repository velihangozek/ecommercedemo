package com.ecommerdedemo.ecommercedemo.business.dtos.response.product;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetProductResponse {

    private int id;
    private String name;
    private double price;
    private int stock;
    private double discountRate;
}
