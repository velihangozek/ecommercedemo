package com.ecommerdedemo.ecommercedemo.repository.abstracts;

import com.ecommerdedemo.ecommercedemo.entities.concretes.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {

}
