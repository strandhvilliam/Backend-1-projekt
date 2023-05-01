package com.example.backend1projekt;

import com.example.backend1projekt.models.Customer;
import com.example.backend1projekt.repositories.ShopOrderRepository;
import com.example.backend1projekt.models.ShopOrder;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ShopOrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ShopOrderRepository shopOrderRepository;

    @BeforeEach
    void clearDatabase() {
        shopOrderRepository.deleteAll();
    }

    @Test
    void testGetAllOrders() throws Exception {
        Customer c1 = new Customer("Test", "12345", new ArrayList<>());
        Customer c2 = new Customer("Test2", "54321", new ArrayList<>());
        ShopOrder order1 = new ShopOrder(LocalDate.now(), c1, new ArrayList<>());
        ShopOrder order2 = new ShopOrder(LocalDate.now(), c2, new ArrayList<>());
        when(shopOrderRepository.findAll()).thenReturn(List.of(order1, order2));

        mockMvc.perform(get("/orders"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetOrderById() throws Exception {
        Customer c2 = new Customer("marcel", "98765", new ArrayList<>());
        ShopOrder order1 = new ShopOrder(LocalDate.now(), c2, new ArrayList<>());
        when(shopOrderRepository.findById(order1.getId())).thenReturn(java.util.Optional.of(order1));

        mockMvc.perform(get("/orders/{id}", order1.getId()))
                .andExpect(status().isOk());
    }

    @Test
    void testGetOrdersByCustomerId() throws Exception {
        Customer c1 = new Customer("Marcel", "98765", new ArrayList<>());
        Customer c2 = new Customer("Sam", "12345", new ArrayList<>());
        Customer c3 = new Customer("Vincent", "87654", new ArrayList<>());
        ShopOrder order1 = new ShopOrder(LocalDate.now(), c1, new ArrayList<>());
        ShopOrder order2 = new ShopOrder(LocalDate.now(), c2, new ArrayList<>());
        ShopOrder order3 = new ShopOrder(LocalDate.now(), c3, new ArrayList<>());

        // Save orders with mock data to repository
        when(shopOrderRepository.findByCustomerId(1L)).thenReturn(Arrays.asList(order1, order2));
        when(shopOrderRepository.findByCustomerId(2L)).thenReturn(List.of(order3));

        mockMvc.perform(get("/orders/customer/{customerId}", 1))
                .andExpect(status().isOk());

        mockMvc.perform(get("/orders/customer/{customerId}", 2))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void testCreateOrder() throws Exception {
        Customer c1 = new Customer("oliver", "87765", new ArrayList<>());
        ShopOrder order = new ShopOrder(LocalDate.now(), c1, new ArrayList<>());

        // Save order with mock data to repository
        when(shopOrderRepository.save(any(ShopOrder.class))).thenReturn(order);

        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(order)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists());
    }
}