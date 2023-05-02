package com.example.backend1projekt.models;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonBackReference;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Entity
public class ShopOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    @ManyToOne (cascade = CascadeType.ALL)
    private Customer customer;

    @ManyToMany
    private List<Item> items;

    public ShopOrder() {
    }

    public ShopOrder(LocalDate date, Customer customer, List<Item> items) {
        this.date = date;
        this.customer = customer;
        this.items = items;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShopOrder shopOrder = (ShopOrder) o;
        return Objects.equals(id, shopOrder.id) && Objects.equals(date, shopOrder.date) && Objects.equals(customer, shopOrder.customer) && Objects.equals(items, shopOrder.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, customer, items);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", date=" + date +
                ", customer=" + customer +
                ", items=" + items +
                '}';
    }
}

