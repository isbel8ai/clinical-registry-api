package org.isbel8ai.training.clinic.service;

import org.isbel8ai.training.clinic.model.Patient;
import org.isbel8ai.training.clinic.rest.dto.NewPatientRequest;

import java.util.List;

public interface PatientService {

    Patient createPatient(NewPatientRequest newPatientRequest);

    Patient getPatient(Long patientId);

    Patient getPatientByEmail(String email);

    List<Patient> getAllPatients();
}
