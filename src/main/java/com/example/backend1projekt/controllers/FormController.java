package com.example.backend1projekt.controllers;

import com.example.backend1projekt.models.Customer;
import com.example.backend1projekt.models.Item;
import com.example.backend1projekt.models.OrderFormAttribute;
import com.example.backend1projekt.models.ShopOrder;
import com.example.backend1projekt.repositories.CustomerRepository;
import com.example.backend1projekt.repositories.ItemRepository;
import com.example.backend1projekt.repositories.ShopOrderRepository;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
        return "redirect:/form/success";
    }

    @GetMapping("/form/success")
    public String getSuccess () {
        return "success";
    }
}
