package com.example.backend1projekt;

import com.example.backend1projekt.models.Customer;
import com.example.backend1projekt.repositories.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
public class DataLoader implements CommandLineRunner {

    private final CustomerRepository customerRepository;

    public DataLoader(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }
    @Override
    public void run(String... args) throws Exception {

        this.customerRepository.save(new Customer("Kalle", "1234567890"));
        this.customerRepository.save(new Customer("Pelle", "0987654321"));
        this.customerRepository.save(new Customer("Nisse", "1231231231"));

    }
}
