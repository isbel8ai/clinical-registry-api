package org.isbel8ai.training.clinic.service.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.isbel8ai.training.clinic.service.JwtTokenService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class JwtTokenServiceImpl implements JwtTokenService {

    private static final long USER_TOKEN_EXPIRATION_TIME = 24 * 60 * 60 * 1000L; // 1 hour
    private static final long PASSWORD_RESET_TOKEN_EXPIRATION_TIME = 15 * 60 * 1000L; // 15 minutes

    @Value("${jwt.secret}")
    private String secret;

    @Override
    public String generateUserToken(String username) {
        Map<String, Object> claims = Map.of();
        return createToken(claims, username, USER_TOKEN_EXPIRATION_TIME);
    }

    @Override
    public String generatePasswordResetToken(String username) {
        Map<String, Object> claims = Map.of("purpose", "passwordReset");
        return createToken(claims, username, PASSWORD_RESET_TOKEN_EXPIRATION_TIME);
    }

    @Override
    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    @Override
    public boolean isValidPasswordResetToken(String token) {
        Claims claims = extractAllClaims(token);
        return claims.getExpiration().after(new Date()) && "passwordReset".equals(claims.get("purpose"));
    }

    @Override
    public String extractUsername(String token) {
        final Claims claims = extractAllClaims(token);
        return claims.getSubject();
    }

    private String createToken(Map<String, Object> claims, String userName, Long expirationTime) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userName)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getSignKey()).build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(String token) {
        Date expiration = extractAllClaims(token).getExpiration();
        return expiration.before(new Date());
    }
}
