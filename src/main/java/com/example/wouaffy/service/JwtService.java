package com.example.wouaffy.service;

import java.security.Key;
import java.text.ParseException;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.wouaffy.entity.User;
import com.nimbusds.jwt.JWTClaimNames;
import com.nimbusds.jwt.JWTParser;

import org.slf4j.Logger;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

  private final String ENCRYPTION_KEY = System.getenv("ENCRYPTION_KEY");

  Logger log = LoggerFactory.getLogger(JwtService.class);

  @Autowired
  private UserService userService;

  public Map<String, String> generate(String email) {

    User user = (User) this.userService.loadUserByUsername(email);
    return this.generateJwt(user);
  }

  private Map<String, String> generateJwt(User user) {

    log.info(ENCRYPTION_KEY);

    final byte[] decoder = Base64.getDecoder().decode(ENCRYPTION_KEY);
    final Key hmacKey = Keys.hmacShaKeyFor(decoder);

    final long currentTime = System.currentTimeMillis();
    final long expirationTime = currentTime + 30 * 60 * 1000;

    final Map<String, Object> claims = Map.of(
        "username", user.getUsername(),
        "email", user.getEmail(),
        JWTClaimNames.EXPIRATION_TIME, new Date(expirationTime),
        JWTClaimNames.SUBJECT, user.getEmail());

    final String token = Jwts.builder()
        .issuedAt(new Date())
        .expiration(new Date(System.currentTimeMillis() + expirationTime))
        .subject(user.getEmail())
        .claims(claims)
        .signWith(hmacKey)
        .compact();
    return Map.of("Token", token);
  }

  public Date getExpirationDateFromToken(String token) throws ParseException {
    return JWTParser.parse(token).getJWTClaimsSet().getExpirationTime();

  }

  public String getClaimByName(String token, String claim) throws ParseException {
    return JWTParser.parse(token).getJWTClaimsSet().getStringClaim(claim);
  }

  public boolean isTokenExpired(String token) throws ParseException {
    Date expiratioDate = getExpirationDateFromToken(token);
    return expiratioDate.before(new Date());
  }
}
