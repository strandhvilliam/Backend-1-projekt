package com.example.backend1projekt.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
public class Item {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private int price;

    @ManyToMany(mappedBy = "items")
    @JsonIgnore
    private List<ShopOrder> shopOrders;

    public Item() {
    }

    public Item (String name, int price, List<ShopOrder> shopOrders) {
        this.name = name;
        this.price = price;
        this.shopOrders = shopOrders;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public List<ShopOrder> getShopOrders() {
        return shopOrders;
    }

    public void setShopOrders(List<ShopOrder> shopOrders) {
        this.shopOrders = shopOrders;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Objects.equals(id, item.id) && Objects.equals(name, item.name) && Objects.equals(price, item.price);
    }
}
