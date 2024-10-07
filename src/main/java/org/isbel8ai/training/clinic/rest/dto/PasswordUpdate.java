package org.isbel8ai.training.clinic.rest.dto;

public record PasswordUpdate(
        String currentPassword,
        String newPassword
) {
}
