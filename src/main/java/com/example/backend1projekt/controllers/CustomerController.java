package com.example.backend1projekt.controllers;


import com.example.backend1projekt.models.Customer;
import com.example.backend1projekt.repositories.CustomerRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/customers")
    public String createCustomer(@RequestBody Customer customer) {
        this.repository.save(customer);
        return "Customer " + customer.getName() + " created";
    }
}
