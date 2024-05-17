package com.ecommerdedemo.ecommercedemo.business.dtos;


import com.ecommerdedemo.ecommercedemo.entities.concretes.Product;
import lombok.Data;

import java.util.List;

@Data
public class CartDTO {
    private Long id;
    private double totalPrice;
    private List<ProductDTO> productDTOS;

    private Long customerId;

    private CustomerDTO customerDTO;
}
