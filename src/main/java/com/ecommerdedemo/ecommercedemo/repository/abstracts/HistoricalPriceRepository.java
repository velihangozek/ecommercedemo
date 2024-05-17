package com.ecommerdedemo.ecommercedemo.repository.abstracts;


import com.ecommerdedemo.ecommercedemo.entities.concretes.HistoricalPrice;
import com.ecommerdedemo.ecommercedemo.entities.concretes.Order;
import com.ecommerdedemo.ecommercedemo.entities.concretes.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoricalPriceRepository extends JpaRepository<HistoricalPrice, Long> {
    public List<HistoricalPrice> findByProduct(Product product);

    public List<HistoricalPrice> findByOrder(Order order);


}
