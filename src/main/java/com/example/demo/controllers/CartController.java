package com.example.demo.controllers;

import java.util.Optional;
import java.util.stream.IntStream;



import jdk.jfr.internal.LogLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.ModifyCartRequest;



@RestController
@RequestMapping("/api/cart")
public class CartController {

	private static final Logger log = LoggerFactory.getLogger(CartController.class);

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CartRepository cartRepository;
	
	@Autowired
	private ItemRepository itemRepository;
	
	@PostMapping("/addToCart")
	public ResponseEntity<Cart> addTocart(@RequestBody ModifyCartRequest request) {
		log.info("Started add to cart");
		User user = userRepository.findByUsername(request.getUsername());
		log.info("Found the user : " + user);
		if(user == null) {
			log.warn("The user "+ user  + "not found");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		Optional<Item> item = itemRepository.findById(request.getItemId());
		log.info("founded the item " + item);
		if(!item.isPresent()) {
			log.warn("the item : "  + item + " not found");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		Cart cart = user.getCart();
		log.info("Created the cart");
		IntStream.range(0, request.getQuantity())
			.forEach(i -> cart.addItem(item.get()));
		log.info("Added all the items to the cart");
		cartRepository.save(cart);
		log.debug("Saved the cart : " , cart);
		return ResponseEntity.ok(cart);
	}
	
	@PostMapping("/removeFromCart")
	public ResponseEntity<Cart> removeFromcart(@RequestBody ModifyCartRequest request) {
		log.info("Started remove cart");
		User user = userRepository.findByUsername(request.getUsername());
		log.info("Found the user : " + user);
		if(user == null) {
			log.warn("The user "+ user  + "not found");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		Optional<Item> item = itemRepository.findById(request.getItemId());
		log.info("founded the item " + item);
		if(!item.isPresent()) {
			log.warn("the item : "  + item + " not found");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		Cart cart = user.getCart();
		log.info("Created the cart");
		IntStream.range(0, request.getQuantity())
			.forEach(i -> cart.removeItem(item.get()));
		log.info("Added all the items to the cart");log.info("Added all the items to the cart");
		cartRepository.save(cart);
		log.debug("Saved the cart : " , cart);
		return ResponseEntity.ok(cart);
	}
		
}
