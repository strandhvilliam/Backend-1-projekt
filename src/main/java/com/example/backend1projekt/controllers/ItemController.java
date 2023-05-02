package com.example.backend1projekt.controllers;


import com.example.backend1projekt.models.Customer;
import com.example.backend1projekt.models.Item;
import com.example.backend1projekt.models.ShopOrder;
import com.example.backend1projekt.repositories.CustomerRepository;
import com.example.backend1projekt.repositories.ItemRepository;
import com.example.backend1projekt.repositories.ShopOrderRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class ItemController {

    private final ItemRepository itemRepository;
    private final CustomerRepository customerRepository;
    private final ShopOrderRepository shopOrderRepository;

    public ItemController (ItemRepository itemRepository, CustomerRepository customerRepository,
                           ShopOrderRepository shopOrderRepository) {
        this.itemRepository = itemRepository;
        this.shopOrderRepository = shopOrderRepository;
        this.customerRepository = customerRepository;
    }

    @GetMapping ("/items")
    public List<Item> getAllItems () {
        return this.itemRepository.findAll();
    }

    @GetMapping ("/item/{id}")
    public Item getItemById (@PathVariable long id) {
        return this.itemRepository.findById(id).orElse(null);
    }

    @PostMapping("/item/add")
    public String addNewItem (@RequestBody Item item) {
        this.itemRepository.save(item);
        return "Item " + item.getName() + " created";
    }

    @PostMapping ("/items/buy")
    public String newShopOrderByID (@RequestBody Map <String, Long> body) {
        Long itemID = body.get("itemID");
        Long customerID = body.get("customerID");
        Item i = this.itemRepository.findById(itemID).orElse(null);
        Customer c = this.customerRepository.findById(customerID).orElse(null);
        ShopOrder shopOrder = new ShopOrder(LocalDate.now(), c, List.of(i));
        this.shopOrderRepository.save(shopOrder);
        return "Customer " + shopOrder.getCustomer().getName() +
                " made a new purchase on Item " + shopOrder.getItems().get(0).getName();
    }




}
