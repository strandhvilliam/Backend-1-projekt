package com.example.backend1projekt.controllers;


import com.example.backend1projekt.models.Customer;
import com.example.backend1projekt.repositories.CustomerRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CustomerController {

    private final CustomerRepository repository;

    public CustomerController(CustomerRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/customers")
    public List<Customer> getCustomers() {
        return this.repository.findAll();
    }

    @GetMapping("/customers/{id}")
    public Customer getCustomerById(@PathVariable Long id) {
        return this.repository.findById(id).orElse(null);
    }
}
