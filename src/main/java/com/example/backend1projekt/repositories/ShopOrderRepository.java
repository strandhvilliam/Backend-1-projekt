package com.example.backend1projekt.repositories;

import com.example.backend1projekt.models.ShopOrder;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShopOrderRepository extends JpaRepository<ShopOrder, Long> {
    List<ShopOrder> findByCustomerId(Long customerId);
}

