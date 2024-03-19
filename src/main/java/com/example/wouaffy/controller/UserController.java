package com.example.wouaffy.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.wouaffy.ResponseData;
import com.example.wouaffy.entity.User;
import com.example.wouaffy.service.UserService;

@RestController
public class UserController {

  @Autowired
  private UserService userService;

  private ResponseData responseData = new ResponseData();

  @PostMapping(path = "/activation")
  public ResponseEntity<ResponseData> accountActivation(@RequestBody Map<String, String> activation) {
    
    if(activation.get("code").isEmpty()) {
      responseData.setCode(400);
      responseData.setMessage("Code value cannot be empty.");
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
    }

    this.userService.activationAccount(activation);
    responseData.setCode(200);
    responseData.setMessage("Account activated successfully.");
    return ResponseEntity.status(HttpStatus.ACCEPTED).body(responseData);
  }

  @PostMapping(path = "signup")
  public ResponseEntity<ResponseData> signUp(@RequestBody User user) {
    
    if (user.getEmail().isEmpty() || user.getUsername().isEmpty() || user.getPassword().isEmpty()) {
      responseData.setCode(400);
      responseData.setMessage("Values cannot be null.");
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
    }

    this.userService.signUp(user);
    responseData.setCode(200);
    responseData.setMessage( "Account created successfully.");
    return ResponseEntity.status(HttpStatus.ACCEPTED).body(responseData);
  }

}
