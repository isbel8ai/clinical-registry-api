package org.isbel8ai.training.clinic.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {

    String extractUsername(String token);

    String generateToken(String username);

    boolean validateToken(String token, UserDetails userDetails);
}
