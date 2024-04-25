package org.isbel8ai.training.clinic.api.dto;

public record PasswordUpdate(
        String currentPassword,
        String newPassword
) {
}
