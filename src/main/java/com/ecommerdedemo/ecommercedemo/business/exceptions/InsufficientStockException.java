package com.ecommerdedemo.ecommercedemo.business.exceptions;

public class InsufficientStockException extends RuntimeException {
    public InsufficientStockException(String productName) {
        super(productName);
    }
}
