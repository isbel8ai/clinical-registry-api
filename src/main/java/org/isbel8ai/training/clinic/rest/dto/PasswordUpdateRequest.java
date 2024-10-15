package org.isbel8ai.training.clinic.rest.dto;

public record PasswordUpdateRequest(
        String currentPassword,
        String newPassword
) {
}
