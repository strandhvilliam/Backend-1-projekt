package com.example.backend1projekt;

import com.example.backend1projekt.models.Customer;
import com.example.backend1projekt.models.Item;
import com.example.backend1projekt.models.ShopOrder;
import com.example.backend1projekt.repositories.CustomerRepository;
import com.example.backend1projekt.repositories.ItemRepository;
import com.example.backend1projekt.repositories.ShopOrderRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;


@Component
public class DataLoader implements CommandLineRunner {

    private final CustomerRepository customerRepository;

    private final ItemRepository itemRepository;


    public DataLoader(CustomerRepository customerRepository, ItemRepository itemRepository) {
        this.customerRepository = customerRepository;
        this.itemRepository = itemRepository;
    }
    @Override
    public void run(String... args) throws Exception {

        this.customerRepository.save(new Customer("Kalle", "1234567890", new ArrayList<>()));
        this.customerRepository.save(new Customer("Pelle", "0987654321", new ArrayList<>()));
        this.customerRepository.save(new Customer("Nisse", "1231231231", new ArrayList<>()));

        this.itemRepository.save(new Item("Banan", 10, new ArrayList<>()));
        this.itemRepository.save(new Item("Äpple", 20, new ArrayList<>()));


    }
}
