package com.example.demo.controller;

import com.example.demo.TestUtils;
import com.example.demo.controllers.ItemController;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.repositories.ItemRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ItemControllerTests {

    /*
    @Autowired
	private ItemRepository itemRepository;

	@GetMapping
	public ResponseEntity<List<Item>> getItems() {
		return ResponseEntity.ok(itemRepository.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Item> getItemById(@PathVariable Long id) {
		return ResponseEntity.of(itemRepository.findById(id));
	}

	@GetMapping("/name/{name}")
	public ResponseEntity<List<Item>> getItemsByName(@PathVariable String name) {
		List<Item> items = itemRepository.findByName(name);
		return items == null || items.isEmpty() ? ResponseEntity.notFound().build()
				: ResponseEntity.ok(items);

	}
     */

    ItemController itemController  ;
    ItemRepository itemRepository = mock(ItemRepository.class);

    @Before
    public void setup(){
        itemController = new ItemController();
        TestUtils.injectObjects(itemController, "itemRepository" , itemRepository);
    }

    @Test
    public void testGetAll(){
        Item item = new Item();
        item.setId(1L);
        item.setName("item 1");
        item.setDescription("item sescription");
        item.setPrice(new BigDecimal(10.0));
       when(itemRepository.findAll()).thenReturn(Arrays.asList(item));
        ResponseEntity<List<Item>> itemsResponse = itemController.getItems();
        assertNotNull(itemsResponse);
        List<Item> items = itemsResponse.getBody();
        assertEquals(1, items.size());

    }

    @Test
    public void testGetByName(){
        Item item = new Item();
        item.setId(1L);
        item.setName("item 1");
        item.setDescription("item sescription");
        item.setPrice(new BigDecimal(10.0));
        when(itemRepository.findByName("item 1")).thenReturn(Arrays.asList(item));
        ResponseEntity<List<Item>> itemsResponse = itemController.getItemsByName("item 1");
        assertNotNull(itemsResponse);
        List<Item> items = itemsResponse.getBody();
        assertEquals(1, items.size());
    }

}
