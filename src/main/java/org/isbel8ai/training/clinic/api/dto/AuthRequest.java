package org.isbel8ai.training.clinic.api.dto;

public record AuthRequest(
        String username,
        String password
) {
}
