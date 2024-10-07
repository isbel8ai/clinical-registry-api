package org.isbel8ai.training.clinic.rest.dto;

public record AuthRequest(
        String username,
        String password
) {
}
