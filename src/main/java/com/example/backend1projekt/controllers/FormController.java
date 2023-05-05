package com.example.backend1projekt.controllers;

import com.example.backend1projekt.models.*;
import com.example.backend1projekt.repositories.CustomerRepository;
import com.example.backend1projekt.repositories.ItemRepository;
import com.example.backend1projekt.repositories.ShopOrderRepository;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class FormController {

    private final Logger log = org.slf4j.LoggerFactory.getLogger(FormController.class);
    private final ItemRepository itemRepository;
    private final ShopOrderRepository shopOrderRepository;
    private final CustomerRepository customerRepository;

    public FormController (ItemRepository itemRepository, CustomerRepository customerRepository,
                           ShopOrderRepository shopOrderRepository) {
        this.itemRepository = itemRepository;
        this.shopOrderRepository = shopOrderRepository;
        this.customerRepository = customerRepository;

    }

    @GetMapping("/form/order")
    public String getForm (Model model) {
        model.addAttribute("allItems", this.itemRepository.findAll());
        model.addAttribute("orderData", new OrderFormAttribute());
        return "orderForm";
    }

    @PostMapping("/form/order")
    public String postForm (@ModelAttribute OrderFormAttribute body) {
        List<Item> items = body.getItemIds().stream().map(id -> this.itemRepository.findById(id).orElse(null)).toList();
        Customer customer = customerRepository.findBySsn(body.getCustomerSsn());
        ShopOrder shopOrder = new ShopOrder(LocalDate.now(), customer, items);
        this.shopOrderRepository.save(shopOrder);
        return "redirect:/form/success/order";
    }

    @GetMapping("/form/customer")
    public String getCustomerForm (Model model) {
        model.addAttribute("customerData", new CustomerFormAttribute());
        return "customerForm";
    }

    @PostMapping("/form/customer")
    public String postCustomerForm (@ModelAttribute CustomerFormAttribute body) {
        Customer customer = new Customer(body.getName(), body.getSsn());
        this.customerRepository.save(customer);
        return "redirect:/form/success/" + customer.getName();
    }

    @GetMapping("/form/item")
    public String getItemForm (Model model) {
        model.addAttribute("itemData", new ItemFormAttribute());
        return "itemForm";
    }

    @PostMapping("/form/item")
    public String postItemForm (@ModelAttribute ItemFormAttribute body) {
        Item item = new Item(body.getName(), body.getPrice());
        this.itemRepository.save(item);
        return "redirect:/form/success/" + item.getName();
    }

    @GetMapping("/form/success/{message}")
    public String getSuccess (@PathVariable String message, Model model) {
        model.addAttribute("message", "Successfully added " + message + " to database" );
        return "success";
    }
}
