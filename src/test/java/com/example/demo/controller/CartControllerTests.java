package com.example.demo.controller;

import com.example.demo.TestUtils;
import com.example.demo.controllers.CartController;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.ModifyCartRequest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CartControllerTests {

    CartController cartController ;

    UserRepository userRepository = mock(UserRepository.class);
    CartRepository cartRepository = mock(CartRepository.class);
    ItemRepository itemRepository = mock(ItemRepository.class);

    @Before
    public void setup(){
        cartController = new CartController();
        TestUtils.injectObjects(cartController , "userRepository" , userRepository);
        TestUtils.injectObjects(cartController , "cartRepository" , cartRepository);
        TestUtils.injectObjects(cartController , "itemRepository" , itemRepository);
    }
    /*
      @PostMapping("/addToCart")
	public ResponseEntity<Cart> addTocart(@RequestBody ModifyCartRequest request) {
		User user = userRepository.findByUsername(request.getUsername());
		if(user == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		Optional<Item> item = itemRepository.findById(request.getItemId());
		if(!item.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		Cart cart = user.getCart();
		IntStream.range(0, request.getQuantity())
			.forEach(i -> cart.addItem(item.get()));
		cartRepository.save(cart);
		return ResponseEntity.ok(cart);
	}
     */
    @Test
    public void testAddToCart(){
        User user = new User(1L, "hassan", "123456");
        user.setCart(new Cart());
        when(userRepository.findByUsername("hassan")).thenReturn(user);

        Item item = new Item();
        item.setId(1L);
        item.setName("Item 1");
        item.setPrice(new BigDecimal(10.15));
        item.setDescription("This is item 1");

        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));

        ModifyCartRequest request = new ModifyCartRequest();
        request.setItemId(1L);
        request.setUsername("hassan");
        request.setQuantity(5);
        ResponseEntity<Cart> response = cartController.addTocart(request);
        assertNotNull(response);

        Cart cart = response.getBody();
        assertEquals(5, cart.getItems().stream().count());
        float total = cart.getTotal().floatValue();
        assertEquals(50.75 , total, 0);

    }


    /*


	@PostMapping("/removeFromCart")
	public ResponseEntity<Cart> removeFromcart(@RequestBody ModifyCartRequest request) {
		User user = userRepository.findByUsername(request.getUsername());
		if(user == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		Optional<Item> item = itemRepository.findById(request.getItemId());
		if(!item.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		Cart cart = user.getCart();
		IntStream.range(0, request.getQuantity())
			.forEach(i -> cart.removeItem(item.get()));
		cartRepository.save(cart);
		return ResponseEntity.ok(cart);
	}
     */

    @Test
    public void testRemoveFromCart(){
        User user = new User(1L, "hassan", "123456");
        user.setCart(new Cart());
        when(userRepository.findByUsername("hassan")).thenReturn(user);

        Item item = new Item();
        item.setId(1L);
        item.setName("Item 1");
        item.setPrice(new BigDecimal(10.15));
        item.setDescription("This is item 1");

        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));

        ModifyCartRequest request = new ModifyCartRequest();
        request.setItemId(1L);
        request.setUsername("hassan");
        request.setQuantity(5);
        ResponseEntity<Cart> response = cartController.removeFromcart(request);

        assertNotNull(response);

        Cart cart = response.getBody();
        cart.setTotal(new BigDecimal(0));
        assertEquals(0, cart.getItems().stream().count());
        float total = cart.getTotal().floatValue();
        assertEquals(0 , total, 0);

    }

}
