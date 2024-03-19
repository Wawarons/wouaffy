package com.example.wouaffy.entity;

import java.time.Instant;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class Validation {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(name = "validation_creation")
  private Instant creation;

  @Column(name = "validation_expiration")
  private Instant expiration;

  @Column(name = "validation_code")
  private String code;

  @OneToOne(cascade = CascadeType.ALL)
  private User user;

  Validation() {};

  Validation(Builder builder) {
    this.creation = builder.creation;
    this.expiration = builder.expiration;
    this.code = builder.code;
    this.user = builder.user;
  }

  public static class Builder {

     private Instant creation;
     private Instant expiration;
     private String code;
     private User user;

     public Builder setCreation(Instant creation) {
      this.creation = creation;
      return this;
     }

     public Builder setExpiration(Instant expiration) {
      this.expiration = expiration;
      return this;
     }
     
     public Builder setCode(String code) {
      this.code = code;
      return this;
     }
     public Builder setUser(User user) {
      this.user = user;
      return this;
     }

     public Validation build() {
      return new Validation(this);
     }
  }
 
  //Getters & Setters
  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public Instant getCreation() {
    return creation;
  }

  public void setCreation(Instant creation) {
    this.creation = creation;
  }

  public Instant getExpiration() {
    return expiration;
  }

  public void setExpiration(Instant expiration) {
    this.expiration = expiration;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }



}
