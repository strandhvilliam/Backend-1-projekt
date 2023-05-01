package com.example.backend1projekt;


import com.example.backend1projekt.models.Customer;
import com.example.backend1projekt.models.ShopOrder;
import com.example.backend1projekt.repositories.ShopOrderRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import java.time.LocalDate;
import java.util.ArrayList;


import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.is;


@SpringBootTest
@AutoConfigureMockMvc
public class OrderControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ShopOrderRepository orderRepository;

    @BeforeEach
    void clearDatabase() {
        orderRepository.deleteAll();
    }

    @Test
    void testGetAllOrders() throws Exception {
        Customer c1 = new Customer("Test", "12345", new ArrayList<>());
        Customer c2 = new Customer("Test2", "54321", new ArrayList<>());
        ShopOrder order1 = new ShopOrder(LocalDate.now(), c1, new ArrayList<>());
        ShopOrder order2 = new ShopOrder(LocalDate.now(), c2, new ArrayList<>());
        orderRepository.save(order1);
        orderRepository.save(order2);

        mockMvc.perform(get("/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].customerId", is(1)))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].customerId", is(2)));
    }

    @Test
    void testGetOrderById() throws Exception {
        Customer c2 = new Customer("marcel", "98765", new ArrayList<>());
        ShopOrder order1 = new ShopOrder(LocalDate.now(), c2, new ArrayList<>());
        orderRepository.save(order1);

        mockMvc.perform(get("/orders/{id}", order1.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.customerId", is(1)));
    }

    @Test
    void testGetOrdersByCustomerId() throws Exception {
        Customer c1 = new Customer("Marcel", "98765", new ArrayList<>());
        Customer c2 = new Customer("Sam", "12345", new ArrayList<>());
        Customer c3 = new Customer("Vincent", "87654", new ArrayList<>());
        ShopOrder order1 = new ShopOrder(LocalDate.now(), c1, new ArrayList<>());
        ShopOrder order2 = new ShopOrder(LocalDate.now(), c2, new ArrayList<>());
        ShopOrder order3 = new ShopOrder(LocalDate.now(), c3, new ArrayList<>());
        orderRepository.save(order1);
        orderRepository.save(order2);
        orderRepository.save(order3);

        mockMvc.perform(get("/orders/customer/{customerId}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].customerId", is(1)))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].customerId", is(1)));
    }

    @Test
    void testCreateOrder() throws Exception {
        Customer c1 = new Customer("oliver", "87765", new ArrayList<>());
        ShopOrder order = new ShopOrder(LocalDate.now(), c1, new ArrayList<>());

        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(order)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.customerId", is(1)));
    }
}
