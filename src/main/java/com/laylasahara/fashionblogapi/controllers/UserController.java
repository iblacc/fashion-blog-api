package com.laylasahara.fashionblogapi.controllers;

import com.laylasahara.fashionblogapi.models.User;
import com.laylasahara.fashionblogapi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("users")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
    @PostMapping("/signup")
    public ResponseEntity<Object> signup(@Valid @RequestBody User user) {
        userService.createUser(user);
        return new ResponseEntity<>("Successfully signed up", HttpStatus.CREATED);
    }
}
