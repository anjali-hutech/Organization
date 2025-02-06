package com.hutech.users.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hutech.users.dto.CreateUserRole;
import com.hutech.users.entity.User;
import com.hutech.users.service.UserService;


@RestController
@RequestMapping("/api/user")
public class UserController {

	   @Autowired
	    private UserService userService;

	    @PostMapping("/signup")
	    public ResponseEntity<CreateUserRole> signup(@RequestBody User user) {
	        CreateUserRole response = userService.signup(user);
	        return ResponseEntity.status(HttpStatus.CREATED).body(response);
	    }
}