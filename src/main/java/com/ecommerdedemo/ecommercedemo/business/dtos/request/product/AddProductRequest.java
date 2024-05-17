package com.ecommerdedemo.ecommercedemo.business.dtos.request.product;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddProductRequest {
    @NotNull
    @NotBlank(message = "name can not be null or empty")
    @Size(min = 3, max = 50, message = "name must be between 3 and 50 characters long")
    private String name;

    @Min(value = 1, message = "price can not be less than 1")
    private double unitPrice;

    @Min(value = 0, message = "Stock amount can not be less than 0")
    private int stock;
    private double discountRate;
}
