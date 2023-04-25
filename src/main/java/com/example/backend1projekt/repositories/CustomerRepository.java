package com.example.backend1projekt.repositories;

import com.example.backend1projekt.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
