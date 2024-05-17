package com.ecommerdedemo.ecommercedemo.business.dtos;


import lombok.Data;

@Data
public class ProductDTO {
    private int id;
    private String name;
    private double price;
    private int stock;
    private double discountRate;
}
