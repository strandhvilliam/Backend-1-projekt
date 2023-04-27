package com.example.backend1projekt.controllers;


import com.example.backend1projekt.models.ShopOrder;
import com.example.backend1projekt.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderRepository repository;

    public OrderController(OrderRepository repository) {
        this.repository = repository;
    }

    @GetMapping("")
    public List<ShopOrder> getAll() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
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



