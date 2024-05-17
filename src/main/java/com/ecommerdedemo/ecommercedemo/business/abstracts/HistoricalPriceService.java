package com.ecommerdedemo.ecommercedemo.business.abstracts;

import com.ecommerdedemo.ecommercedemo.entities.concretes.HistoricalPrice;
import com.ecommerdedemo.ecommercedemo.entities.concretes.Order;
import com.ecommerdedemo.ecommercedemo.entities.concretes.Product;

import java.util.List;

public interface HistoricalPriceService {
    public void saveHistoricalPrice(Product product, double price);

    public List<HistoricalPrice> getHistoricalPricesForRelatedProduct(Product product);

    public List<HistoricalPrice> getHistoricalPricesForRelatedOrder(Order order);
}
