package org.isbel8ai.training.clinic.rest.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record NewPatientRequest(
        @NotBlank String fullName,
        @NotBlank @Email String email
) {}
