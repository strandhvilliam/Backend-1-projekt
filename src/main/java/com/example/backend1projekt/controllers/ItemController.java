package com.example.backend1projekt.controllers;

import com.example.backend1projekt.models.Item;
import com.example.backend1projekt.repositories.ItemRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ItemController {

    private final ItemRepository repository;

    public ItemController (ItemRepository repository) {
        this.repository = repository;
    }

    @GetMapping ("/items")
    public List<Item> getAllItems () {
        return this.repository.findAll();
    }

    @GetMapping ("/item/{id}")
    public Item getItemById (@PathVariable long id) {
        return this.repository.findById(id).orElse(null);
    }


}
