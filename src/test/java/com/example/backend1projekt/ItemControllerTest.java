package com.example.backend1projekt;

import com.example.backend1projekt.models.Customer;
import com.example.backend1projekt.models.Item;
import com.example.backend1projekt.models.ShopOrder;
import com.example.backend1projekt.repositories.CustomerRepository;
import com.example.backend1projekt.repositories.ItemRepository;
import com.example.backend1projekt.repositories.ShopOrderRepository;
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
import java.util.*;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ShopOrderRepository shopOrderRepository;
    @MockBean
    private CustomerRepository customerRepository;
    @MockBean
    private ItemRepository itemRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void init() {

        Item i1 = new Item("Vindruvor", 50, new ArrayList<>());
        Item i2 = new Item("Banan", 25, new ArrayList<>());
        Item i3 = new Item("Papaya", 100, new ArrayList<>());

        when(itemRepository.findById(1L)).thenReturn(Optional.of(i1));
        when(itemRepository.findById(2L)).thenReturn(Optional.of(i2));
        when(itemRepository.findById(3L)).thenReturn(Optional.of(i3));
        when(itemRepository.findAll()).thenReturn(Arrays.asList(i1, i2, i3));

        Customer c = new Customer("Vic", "444555", new ArrayList<>());
        when(customerRepository.findById(1L)).thenReturn(Optional.of(c));

        ShopOrder s1 = new ShopOrder(LocalDate.now(), c, List.of(i1));
        when(shopOrderRepository.findById(1L)).thenReturn(Optional.of(s1));
    }

    @Test
    void testGetAllItems() throws Exception {

        List<Item> correct = List.of(new Item("Vindruvor", 50, new ArrayList<>()),
                new Item("Banan", 25, new ArrayList<>()),
                new Item("Papaya", 100, new ArrayList<>()));

        this.mockMvc.perform(get("/items"))
                .andExpect(status().isOk())
                .andExpect(content().json(
                        objectMapper.writeValueAsString(correct)
                ));
    }

    @Test
    void testGetItemById () throws Exception {

        Item correct = new Item("Vindruvor", 50, new ArrayList<>());
        this.mockMvc.perform(get("/item/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(
                        objectMapper.writeValueAsString(correct)
                ));

    }

    @Test
    void testAddItemByPost() throws Exception {

        this.mockMvc.perform(post("/item/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new Item ("Jacka", 500, new ArrayList<>()))))
                .andExpect(status().isOk())
                .andExpect(content().string("Item Jacka created"));

    }

    @Test
    void testBuyItemByPost() throws Exception {

        Map <String, Long> map = new HashMap<>();
        map.put("customerID", 1L);
        map.put("itemID", 1L);

        this.mockMvc.perform(post("/items/buy")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(map)))
                .andExpect(status().isOk())
                .andExpect(content()
                        .string("Customer Vic made a new purchase on Item Vindruvor"));

    }

}
