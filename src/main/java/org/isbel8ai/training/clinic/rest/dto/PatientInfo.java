package org.isbel8ai.training.clinic.rest.dto;

import org.isbel8ai.training.clinic.model.Patient;

public record PatientInfo(
        Long id,
        String fullName,
        String email
) {
    public PatientInfo(Patient patient) {
        this(
                patient.getId(),
                patient.getAccount().getFullName(),
                patient.getAccount().getEmail()
        );
    }
}
