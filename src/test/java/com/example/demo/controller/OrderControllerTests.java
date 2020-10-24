package com.example.demo.controller;

import com.example.demo.TestUtils;
import com.example.demo.controllers.OrderController;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.UserOrder;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.OrderRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OrderControllerTests {
    /*

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private OrderRepository orderRepository;


	@PostMapping("/submit/{username}")
	public ResponseEntity<UserOrder> submit(@PathVariable String username) {
		User user = userRepository.findByUsername(username);
		if(user == null) {
			return ResponseEntity.notFound().build();
		}
		UserOrder order = UserOrder.createFromCart(user.getCart());
		orderRepository.save(order);
		return ResponseEntity.ok(order);
	}

	@GetMapping("/history/{username}")
	public ResponseEntity<List<UserOrder>> getOrdersForUser(@PathVariable String username) {
		User user = userRepository.findByUsername(username);
		if(user == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(orderRepository.findByUser(user));
	}

     */
    OrderController orderController;

    UserRepository userRepository = mock(UserRepository.class);
    OrderRepository orderRepository = mock(OrderRepository.class);
    ItemRepository itemRepository =  mock(ItemRepository.class);
    @Before
    public void setup(){
        orderController = new OrderController();
        TestUtils.injectObjects(orderController, "userRepository" , userRepository);
        TestUtils.injectObjects(orderController , "orderRepository" , orderRepository);


    }

    @Test
    public void submitOrder(){
        User user = new User(1L, "hassan", "123456");
        Cart cart = new Cart();
        Item item  = new Item();
        item.setId(1L);
        item.setName("name1");
        item.setPrice(new BigDecimal(10));
        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));

        cart.addItem(item);
        user.setCart(cart);


        when(userRepository.findByUsername("hassan")).thenReturn(user);
        ResponseEntity<UserOrder> order = orderController.submit("hassan");
        assertNotNull(order);
    }

}
