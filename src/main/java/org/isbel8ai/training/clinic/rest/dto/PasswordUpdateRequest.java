package org.isbel8ai.training.clinic.rest.dto;

import jakarta.validation.constraints.NotBlank;

public record PasswordUpdateRequest(
        @NotBlank String oldPassword,
        @NotBlank String newPassword
) {}
