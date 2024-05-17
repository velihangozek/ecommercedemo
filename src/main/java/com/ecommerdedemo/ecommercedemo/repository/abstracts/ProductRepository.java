package com.ecommerdedemo.ecommercedemo.repository.abstracts;

import com.ecommerdedemo.ecommercedemo.entities.concretes.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
