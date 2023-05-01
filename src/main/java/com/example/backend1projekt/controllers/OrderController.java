package com.example.backend1projekt.controllers;


import com.example.backend1projekt.models.ShopOrder;
import com.example.backend1projekt.repositories.ShopOrderRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final ShopOrderRepository repository;

    public OrderController(ShopOrderRepository repository) {
        this.repository = repository;
    }

    @GetMapping("")
    public List<ShopOrder> getAllOrders() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    //Returnerar en ResponseEntity som innehåller ShopOrder om den hittas, eller ett 404 Not Found-svar om den inte hittas.
    public ResponseEntity<ShopOrder> getById(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/customer/{customerId}")
    public List<ShopOrder> getOrdersByCustomerId(@PathVariable Long customerId) {
        return repository.findByCustomerId(customerId);
    }

    @PostMapping("")
    // Hanterar POST-anrop för att skapa en ny ShopOrder.
    // Om ordern sparas korrekt returnerar den ett 201 Created-svar med den sparade ShopOrder.
    // Om det uppstår ett fel när ordern sparas returnerar den ett 500 Internal Server Error-svar.
    public ResponseEntity<ShopOrder> createOrder(@RequestBody ShopOrder order) {
        ShopOrder savedOrder = repository.save(order);
        ShopOrder newOrder = repository.findById(savedOrder.getId()).orElse(null);
        if (newOrder != null) {
            return ResponseEntity.created(URI.create("/orders/" + newOrder.getId())).body(newOrder);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}



