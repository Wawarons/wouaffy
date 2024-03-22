package com.example.wouaffy.service;

import java.time.Instant;
import java.util.Map;
import java.util.Optional;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.wouaffy.RoleType;
import com.example.wouaffy.Repository.UserRepository;
import com.example.wouaffy.Repository.ValidationRepository;
import com.example.wouaffy.entity.Role;
import com.example.wouaffy.entity.User;
import com.example.wouaffy.entity.Validation;

@Service
public class UserService implements UserDetailsService {

  @Autowired
  private ValidationRepository validationRepository;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private ValidationService validationService;

  @Autowired
  private BCryptPasswordEncoder passwordEncoder;

  UserService() {
  }

  public void signUp(User user) {
    Optional<User> userFound = this.userRepository.findByEmail(user.getEmail());

    if (userFound.isPresent()) {
      throw new RuntimeErrorException(null, "E-mail already exist");
    }

    String encryptPassword = passwordEncoder.encode(user.getPassword());
    user.setPassword(encryptPassword);
    this.userRepository.save(user);
    this.validationService.createValidation(user);
  }

  public void activationAccount(Map<String, String> activation) {

    Validation validation = this.validationService.readByCode(activation.get("code"));
    if (Instant.now().isAfter(validation.getExpiration())) {
      throw new RuntimeException("This code is expired.");
    }

    User userActive = this.userRepository.findById(validation.getUser().getId())
        .orElseThrow(() -> new RuntimeException("Unknown user."));
    userActive.setActive(true);
    Role role = new Role(RoleType.USER);
    userActive.setRole(role);
    userActive.setCreated_at(Instant.now());
    this.userRepository.save(userActive);
    this.validationRepository.save(validation);
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return this.userRepository.findByEmail(username)
        .orElseThrow(() -> new UsernameNotFoundException("Account not found. for: " + username));
  }

}
