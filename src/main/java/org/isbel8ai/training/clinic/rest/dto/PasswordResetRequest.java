package org.isbel8ai.training.clinic.rest.dto;

import jakarta.validation.constraints.NotBlank;

public record PasswordResetRequest(
        @NotBlank String resetToken,
        @NotBlank String newPassword
) {}
