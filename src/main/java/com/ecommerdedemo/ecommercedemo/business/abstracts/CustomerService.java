package com.ecommerdedemo.ecommercedemo.business.abstracts;

import com.ecommerdedemo.ecommercedemo.business.dtos.CustomerDTO;
import com.ecommerdedemo.ecommercedemo.entities.concretes.Customer;

import java.util.List;

public interface CustomerService {
    public List<Customer> getAllCustomers();

    public Customer getCustomerById(Long id);

    public Customer createCustomer(CustomerDTO customerDTO);

    public Customer updateCustomer(Long id, CustomerDTO customerDTO);

    public void deleteCustomer(Long id);
}
