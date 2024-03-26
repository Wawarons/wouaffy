package com.example.wouaffy.entity;

import java.time.Instant;
import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import io.micrometer.common.lang.NonNull;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "user_account")
public class User implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @NonNull
  @Size(min = 5, max = 20, message = "Username must be between 5 and 20 characters")
  @Column(name = "user_account_username")
  private String username;

  @NonNull
  @Email(message = "Email should be valid")
  @Column(name = "user_account_email")
  private String email;

  @NonNull
  @Column(name = "user_account_password")
  private String password;

  @NonNull
  @Column(name = "user_account_active")
  private boolean isActive = false;

  @Column(name = "user_created_at")
  private Instant created_at;

  @OneToOne(cascade = CascadeType.ALL)
  private Role role;

  User(long id, String username, String password, boolean isActive, Instant created_at, Role role) {
    this.id = id;
    this.password = password;
    this.isActive = isActive;
    this.created_at = created_at;
    this.role = role;
  }

  User() {
  };

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + this.role.getRole()));
  }

  @Override
  public boolean isAccountNonExpired() {
    return this.isActive;
  }

  @Override
  public boolean isAccountNonLocked() {
    return this.isActive;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return this.isActive;
  }

  @Override
  public boolean isEnabled() {
    return this.isActive;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public boolean isActive() {
    return isActive;
  }

  public void setActive(boolean isActive) {
    this.isActive = isActive;
  }

  public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
    this.role = role;
  }

  public Instant getCreated_at() {
    return created_at;
  }

  public void setCreated_at(Instant created_at) {
    this.created_at = created_at;
  }

}
