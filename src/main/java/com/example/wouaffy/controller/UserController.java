package com.example.wouaffy.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.wouaffy.ResponseData;
import com.example.wouaffy.Dto.AuthentificationDTO;
import com.example.wouaffy.entity.User;
import com.example.wouaffy.service.JwtService;
import com.example.wouaffy.service.ResponseService;
import com.example.wouaffy.service.UserService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

@RestController
public class UserController {

  @Autowired
  private UserService userService;

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private ResponseService responseService;

  @Autowired
  private JwtService jwtService;

  ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
  Validator validator = factory.getValidator();

  Logger log = LoggerFactory.getLogger(UserController.class);

  @PostMapping(path = "/auth/activation")
  public ResponseEntity<ResponseData> accountActivation(@RequestBody Map<String, String> activation) {

    if (activation.get("code").isEmpty()) {
      return responseService.createResponse(400, "Code value cannot be empty.");
    }

    userService.activationAccount(activation);
    return responseService.createResponse(200, "Account activated successfully.");
  }

  @PostMapping(path = "/auth/signup")
  public ResponseEntity<String> signUp(@Valid @RequestBody User user) {

    this.userService.signUp(user);
    return ResponseEntity.ok("Account created successfully.");

  }

  @PostMapping(path = "/auth/login")
  public ResponseEntity<String> login(@RequestBody AuthentificationDTO authentificationDTO,
      HttpServletResponse response) {

    final Authentication authenticate = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(authentificationDTO.email(),
            authentificationDTO.password()));

    if (authenticate.isAuthenticated()) {

      Map<String, String> token = this.jwtService.generate(authentificationDTO.email());

      Cookie cookie = new Cookie("token", token.get("Token"));
      cookie.setHttpOnly(true);
      cookie.setSecure(true);
      cookie.setPath("/");
      cookie.setMaxAge(30 * 60);

      response.addCookie(cookie);

      return ResponseEntity.ok("Login successfully.");
    }

    return ResponseEntity.badRequest().body("Bad credentials");
  }

  @RequestMapping(value = "/signout", method = RequestMethod.GET)
  public ResponseEntity<String> signOut(HttpServletResponse response) {
    Cookie cookie = new Cookie("token", "");
    cookie.setPath("/");
    cookie.setMaxAge(0);
    response.addCookie(cookie);
    return ResponseEntity.ok("Sign out successfully.");

  }

}
