package com.ecommerdedemo.ecommercedemo.business.concretes;

import com.ecommerdedemo.ecommercedemo.business.abstracts.CustomerService;
import com.ecommerdedemo.ecommercedemo.business.dtos.CustomerDTO;
import com.ecommerdedemo.ecommercedemo.business.exceptions.ResourceNotFoundException;
import com.ecommerdedemo.ecommercedemo.entities.concretes.Customer;
import com.ecommerdedemo.ecommercedemo.repository.abstracts.CustomerRepository;

import java.util.List;
import java.util.Optional;

public class CustomerManager implements CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerManager(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public List<Customer> getAllCustomers() {
        return this.customerRepository.findAll();
    }

    @Override
    public Customer getCustomerById(Long id) {
        Customer customer = this.customerRepository.findById(id).
                orElseThrow(() ->
                        new ResourceNotFoundException("The customer with the relevant id was not found" + id));
        return customer;
    }

    @Override
    public Customer createCustomer(CustomerDTO customerDTO) {
        Customer customer = new Customer();

        customer.setName(customerDTO.getName());
        customer.setEmail(customerDTO.getEmail());
        customer.setPhone(customerDTO.getPhone());
        customer.setBirthDate(customerDTO.getBirthDate());
        customer.setGender(customerDTO.getGender());

        Customer savedCustomer = this.customerRepository.save(customer);

        return savedCustomer;
    }

    @Override
    public Customer updateCustomer(Long id, CustomerDTO customerDTO) {
        Customer customer = this.customerRepository.findById(id).
                orElseThrow(() ->
                        new ResourceNotFoundException("The customer with the relevant id was not found" + id));

        customer.setName(customerDTO.getName());
        customer.setEmail(customerDTO.getEmail());
        customer.setPhone(customerDTO.getPhone());
        customer.setBirthDate(customerDTO.getBirthDate());
        customer.setGender(customer.getGender());

        Customer customerToBeUpdated = this.customerRepository.save(customer);
        return customerToBeUpdated;

    }

    @Override
    public void deleteCustomer(Long id) {
        Customer customer = this.customerRepository.findById(id).
                orElseThrow(() ->
                        new ResourceNotFoundException("The customer with the relevant id was not found" + id));

        this.customerRepository.delete(customer);
    }
}
