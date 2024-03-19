package com.example.wouaffy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.example.wouaffy.entity.Validation;

@Service
public class NotificationService {

  @Autowired
  private JavaMailSender javaMailSender;

  public void send(Validation validation) {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setFrom("root@localhost");
    message.setTo(validation.getUser().getEmail());
    message.setSubject("Your activation code");
    String messageContent = String.format(
        "Hello %s, < br/> Your activation code is: %s",
        validation.getUser().getUsername(),
        validation.getCode());
    message.setText(messageContent);
    javaMailSender.send(message);
  }
}
