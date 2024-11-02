package org.isbel8ai.training.clinic.rest.dto;

import jakarta.validation.constraints.NotBlank;

public record PasswordSetRequest(
        @NotBlank String newPassword
) {}
