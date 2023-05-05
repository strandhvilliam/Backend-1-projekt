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
import java.util.List;


@Component
public class DataLoader implements CommandLineRunner {

    private final CustomerRepository customerRepository;
    private final ShopOrderRepository shopOrderRepository;
    private final ItemRepository itemRepository;

    public DataLoader(CustomerRepository customerRepository, ItemRepository itemRepository,
                      ShopOrderRepository shopOrderRepository) {
        this.customerRepository = customerRepository;
        this.itemRepository = itemRepository;
        this.shopOrderRepository = shopOrderRepository;
    }
    @Override
    public void run(String... args) throws Exception {

        this.customerRepository.save(new Customer("Kalle", "1234567890"));
        this.customerRepository.save(new Customer("Pelle", "0987654321"));
        this.customerRepository.save(new Customer("Nisse", "1231231231"));

        this.itemRepository.save(new Item("Banan", 10));
        this.itemRepository.save(new Item("Äpple", 20.2));
        this.itemRepository.save(new Item("Päron", 13.3));

        this.shopOrderRepository.save(new ShopOrder(LocalDate.now(), this.customerRepository.findById(3L).orElse(null),
              new ArrayList<>(List.of(this.itemRepository.findById(3L).get()))));
        this.shopOrderRepository.save(new ShopOrder(LocalDate.now(), this.customerRepository.findById(1L).orElse(null),
              new ArrayList<>(List.of(this.itemRepository.findById(1L).get(), this.itemRepository.findById(2L).get()))));
    }
}
