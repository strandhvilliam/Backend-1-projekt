package com.example.backend1projekt.models;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;


    @Entity
    public class Order {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private LocalDate date;

        @ManyToOne
        private Customer customer;

        @ManyToMany
        private List<Item> items;

        public Order() {
        }

        public Order(LocalDate date, Customer customer, List<Item> items) {
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
            Order order = (Order) o;
            return Objects.equals(id, order.id) && Objects.equals(date, order.date) && Objects.equals(customer, order.customer) && Objects.equals(items, order.items);
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

