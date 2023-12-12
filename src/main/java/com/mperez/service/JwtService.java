package com.mperez.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

  @Value("${application.security.jwt.secret-key}")
  private String secretKey;

  public String extractUsername(final String token) {
    return this.extractClaim(token, Claims::getSubject);
  }

  public String generateToken(final UserDetails userDetails) {
    return this.generateToken(new HashMap<>(), userDetails);
  }

  public String generateToken(
      final Map<String, Object> extraClaims, final UserDetails userDetails) {
    final Date currentDate = new Date(System.currentTimeMillis());
    return Jwts.builder()
        .setClaims(extraClaims)
        .setSubject(userDetails.getUsername())
        .setIssuedAt(currentDate)
        .setExpiration(new Date(System.currentTimeMillis() + (24 * 60 * 60 * 1000)))
        .signWith(this.getSigninKey(), SignatureAlgorithm.HS256)
        .compact();
  }

  public boolean isTokenValid(final String token, final UserDetails userDetails) {
    final String username = this.extractUsername(token);

    return username.equals(userDetails.getUsername()) && !this.isTokenExpired(token);
  }

  private boolean isTokenExpired(final String token) {
    return this.extractExpiration(token).before(new Date());
  }

  private Date extractExpiration(final String token) {
    return this.extractClaim(token, Claims::getExpiration);
  }

  private <T> T extractClaim(final String token, final Function<Claims, T> claimsResolver) {
    final Claims claims = this.extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  private Claims extractAllClaims(final String token) {
    return Jwts.parserBuilder()
        .setSigningKey(this.getSigninKey())
        .build()
        .parseClaimsJws(token)
        .getBody();
  }

  private Key getSigninKey() {
    final byte[] keyBytes = Decoders.BASE64.decode(this.secretKey);
    return Keys.hmacShaKeyFor(keyBytes);
  }
}
