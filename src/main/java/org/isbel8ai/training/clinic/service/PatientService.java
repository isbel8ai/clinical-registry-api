package org.isbel8ai.training.clinic.service;

import org.isbel8ai.training.clinic.model.Patient;

public interface PatientService {

    Patient createPatient(String fullName, String email);

    Patient getPatientById(Long patientId);
}
