package com.example.demo.controller;

import com.example.demo.TestUtils;
import com.example.demo.controllers.UserController;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserControllerTests {
    private UserController userController;

    private UserRepository userRepository = mock(UserRepository.class);

    private CartRepository cartRepository = mock(CartRepository.class);

    private BCryptPasswordEncoder encoder  = mock(BCryptPasswordEncoder.class);

    @Before
    public void setup(){
        userController = new UserController();
        TestUtils.injectObjects(userController, "userRepository" , userRepository);
        TestUtils.injectObjects(userController, "cartRepository", cartRepository);
        TestUtils.injectObjects(userController, "bCryptPasswordEncoder" ,encoder);
    }

    @Test
    public void testCreateUser(){
        when(encoder.encode("testPassword")).thenReturn("hashedPassword");

        CreateUserRequest request = new CreateUserRequest();
        request.setUsername("hassan");
        request.setPassword("testPassword");
        request.setConfirmPassword("testPassword");

        ResponseEntity<User> response = userController.createUser(request);
        assertNotNull(response);

        User user = response.getBody();
        assertNotNull(user);
        assertEquals("hashedPassword", user.getPassword());




    }

    @Test
    public void testGetByID(){
        User user = new User(1L, "hassan","password");
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        ResponseEntity<User> response = userController.findById(1L);
        User user1 = response.getBody();
        assertNotNull(user1);
        assertEquals(1L, user1.getId());

    }


    @Test
    public void testByUserName(){
        User user = new User( "hassan","password");
        user.setId(1L);
        when(userRepository.findByUsername("hassan")).thenReturn(user);

        ResponseEntity<User> response = userController.findByUserName("hassan");
        User user1 = response.getBody();
        assertNotNull(user1);
        assertEquals("hassan", user1.getUsername());

    }

}
