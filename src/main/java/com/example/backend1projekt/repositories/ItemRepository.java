package com.example.backend1projekt.repositories;

import com.example.backend1projekt.models.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository <Item, Long> {
}
