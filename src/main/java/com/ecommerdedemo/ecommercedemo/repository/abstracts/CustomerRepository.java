package com.ecommerdedemo.ecommercedemo.repository.abstracts;

import com.ecommerdedemo.ecommercedemo.business.concretes.CustomerManager;
import com.ecommerdedemo.ecommercedemo.entities.concretes.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
