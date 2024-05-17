package com.ecommerdedemo.ecommercedemo.business.dtos;


import com.ecommerdedemo.ecommercedemo.entities.concretes.Cart;
import com.ecommerdedemo.ecommercedemo.entities.concretes.Customer;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data

public class OrderDTO {
    private int id;
    private LocalDate orderDate;
    private String paymentMethod;
    private boolean isCompleted;


}
