package com.example.wouaffy.controller;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.wouaffy.entity.User;
import com.example.wouaffy.service.UserService;

import ch.qos.logback.classic.Logger;

@RestController
public class UserController {
	
	@Autowired
	private UserService userService;
	
	Logger logger = (Logger) LoggerFactory.getLogger(UserController.class);
	
	@GetMapping(path = "hello")
	public String hello() {
		return "Hello";
	}
	
	@PostMapping(path = "signup")
	public void signUp(@RequestBody User user) {
		this.userService.signUp(user);
		logger.info("Inscription");
	}
	
	
}
