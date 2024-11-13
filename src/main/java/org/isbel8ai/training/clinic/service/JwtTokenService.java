package org.isbel8ai.training.clinic.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtTokenService {

    boolean isValidPasswordResetToken(String token);

    String extractUsername(String token);

    String generateUserToken(String username);

    String generatePasswordResetToken(String userId);

    boolean validateToken(String token, UserDetails userDetails);
}
