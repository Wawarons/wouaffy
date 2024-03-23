package com.example.wouaffy.security;

import java.io.IOException;
import java.text.ParseException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.wouaffy.service.CookieService;
import com.example.wouaffy.service.JwtService;
import com.example.wouaffy.service.UserService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class JwtFilter extends OncePerRequestFilter {

  private UserService userService;
  private JwtService jwtService;
  private CookieService cookieService;

  private final RequestMatcher ignoredPaths = new AntPathRequestMatcher("/auth/**");

  public JwtFilter(UserService userService, JwtService jwtService, CookieService cookieService) {
    this.userService = userService;
    this.jwtService = jwtService;
    this.cookieService = cookieService;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
      FilterChain filterChain) throws ServletException, IOException {

    if (this.ignoredPaths.matches(httpServletRequest)) {
      filterChain.doFilter(httpServletRequest, httpServletResponse);
      return;
    }

    String token = null;
    String email = null;
    boolean isTokenExpired = true;
    token = cookieService.getCookieByName(httpServletRequest, "token").getValue();

    if (token != null) {

      try {
        isTokenExpired = jwtService.isTokenExpired(token);
      } catch (ParseException e) {
        e.printStackTrace();
      }

      try {
        email = (String) jwtService.getClaimByName(token, "email");
      } catch (ParseException e) {
        e.printStackTrace();
      }
    }

    if (!isTokenExpired && email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
      UserDetails user = userService.loadUserByUsername(email);
      UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user,
          null, user.getAuthorities());
      SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }
    filterChain.doFilter(httpServletRequest, httpServletResponse);

  }
}
