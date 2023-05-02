package com.example.backend1projekt.controllers;

import com.example.backend1projekt.repositories.CustomerRepository;
import com.example.backend1projekt.repositories.ItemRepository;
import com.example.backend1projekt.repositories.ShopOrderRepository;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    private final Logger log = org.slf4j.LoggerFactory.getLogger(FormController.class);
    private final ItemRepository itemRepository;
    private final ShopOrderRepository shopOrderRepository;
    private final CustomerRepository customerRepository;

    public ViewController (ItemRepository itemRepository, CustomerRepository customerRepository,
                           ShopOrderRepository shopOrderRepository) {
        this.itemRepository = itemRepository;
        this.shopOrderRepository = shopOrderRepository;
        this.customerRepository = customerRepository;

    }

    @GetMapping("/view")
    public String getHome (Model model) {
        model.addAttribute("title", "Backend-1 Project Links");
        model.addAttribute("showItemsTxt", "Show items");
        model.addAttribute("showCustomersTxt", "Show customers");
        model.addAttribute("showOrdersTxt", "Show orders");
        model.addAttribute("formOrderTxt", "Order an item");
        model.addAttribute("formCustomerTxt", "Add a customer");
        model.addAttribute("formItemTxt", "Add an item");
        return "index";
    }

    @GetMapping("/view/items")
    public String getItems (Model model) {
        model.addAttribute("title", "All Items");
        model.addAttribute("items", this.itemRepository.findAll());
        return "itemsView";
    }

    @GetMapping("/view/customers")
    public String getCustomers (Model model) {
        model.addAttribute("title", "All Customers");
        model.addAttribute("customers", this.customerRepository.findAll());
        return "customersView";
    }

    @GetMapping("/view/orders")
    public String getOrders (Model model) {
        model.addAttribute("title", "All Orders");
        model.addAttribute("orders", this.shopOrderRepository.findAll());
        return "ordersView";
    }
}
