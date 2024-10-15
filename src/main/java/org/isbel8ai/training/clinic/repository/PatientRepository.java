package org.isbel8ai.training.clinic.repository;

import org.isbel8ai.training.clinic.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long> {
}
