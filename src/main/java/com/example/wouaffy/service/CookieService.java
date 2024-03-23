package com.example.wouaffy.service;

import org.springframework.stereotype.Service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class CookieService {

  public Cookie getCookieByName(HttpServletRequest request, String name) {

    Cookie[] cookies = request.getCookies();

    if (cookies != null) {
      for (Cookie cookie : cookies) {
        if (cookie.getName().equals(name)) {
          return cookie;
        }
      }
    }
    return null;
  }
}
