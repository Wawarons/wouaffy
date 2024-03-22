package com.example.wouaffy.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.wouaffy.Repository.ValidationRepository;
import com.example.wouaffy.entity.User;
import com.example.wouaffy.entity.Validation;

@Service
public class ValidationService {

  @Autowired
  private ValidationRepository validationRepository;

  @Autowired
  private NotificationService notificationService;

  public String generateCode() {
    Random random = new Random();
    return String.format("%d6", random.nextInt(99999));
  }

  public void createValidation(User user) {
    Instant creation = Instant.now();

    Validation validation = new Validation.Builder()
        .setCreation(creation)
        .setExpiration(creation.plus(10, ChronoUnit.MINUTES))
        .setCode(generateCode())
        .setUser(user).build();

    this.validationRepository.save(validation);
    this.notificationService.send(validation);
  }

  public Validation readByCode(String code) {

    return this.validationRepository.findByCode(code)
        .orElseThrow(() -> new RuntimeException("Invalid code."));

  }

}
