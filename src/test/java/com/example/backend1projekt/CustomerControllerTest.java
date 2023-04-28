package com.example.backend1projekt;


import com.example.backend1projekt.models.Customer;
import com.example.backend1projekt.repositories.CustomerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerRepository mockRepo;

    @BeforeEach
    public void init() {
        Customer c1 = new Customer("Test", "12345", new ArrayList<>());
        Customer c2 = new Customer("Test2", "54321", new ArrayList<>());

        when(mockRepo.findById(1L)).thenReturn(Optional.of(c1));
        when(mockRepo.findById(2L)).thenReturn(Optional.of(c2));
        when(mockRepo.findAll()).thenReturn(Arrays.asList(c1, c2));

    }

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Logger logger = org.slf4j.LoggerFactory.getLogger(CustomerControllerTest.class);

    @Test
   void testGetCustomers() throws Exception {

        List<Customer> correct = List.of(new Customer("Test", "12345", new ArrayList<>()), new Customer("Test2", "54321", new ArrayList<>()));

        this.mockMvc.perform(get("/customers"))
                .andExpect(status().isOk())
                .andExpect(content().json(
                        objectMapper.writeValueAsString(correct)
                ));
    }

    @Test
    void testGetCustomerById() throws Exception {

        Customer correct = new Customer("Test", "12345", new ArrayList<>());

        this.mockMvc.perform(get("/customers/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(
                        objectMapper.writeValueAsString(correct)
                ));
    }


    @Test
    void testPostCustomer() throws Exception {

        this.mockMvc.perform(post("/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new Customer("Test", "12345", new ArrayList<>()))))
                .andExpect(status().isOk())
                .andExpect(content().string("Customer Test created"));

    }

}
