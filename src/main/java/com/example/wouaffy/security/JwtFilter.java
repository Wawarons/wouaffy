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

  private final RequestMatcher ignoredPaths = new AntPathRequestMatcher("/auth/**");

  public JwtFilter(UserService userService, JwtService jwtService) {
    this.userService = userService;
    this.jwtService = jwtService;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
      FilterChain filterChain) throws ServletException, IOException {

    if (this.ignoredPaths.matches(httpServletRequest)) {
      filterChain.doFilter(httpServletRequest, httpServletResponse);
      return;
    }

    String token = null;
    String username = null;
    boolean isTokenExpired = true;
    final String authorization = httpServletRequest.getHeader("authorization");

    if (!authorization.isEmpty() && authorization.startsWith("Bearer")) {
      token = authorization.substring(7);

      try {
        isTokenExpired = jwtService.isTokenExpired(token);
      } catch (ParseException e) {
        e.printStackTrace();
      }

      try {
        username = (String) jwtService.extractClaim(token, "email");
      } catch (ParseException e) {
        e.printStackTrace();
      }
    }

    if (!isTokenExpired && username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
      UserDetails user = userService.loadUserByUsername(username);
      UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user,
          null, user.getAuthorities());
      SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }
    filterChain.doFilter(httpServletRequest, httpServletResponse);

  }
}
