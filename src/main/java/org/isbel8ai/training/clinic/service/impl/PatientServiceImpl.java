package org.isbel8ai.training.clinic.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.isbel8ai.training.clinic.model.Account;
import org.isbel8ai.training.clinic.model.Patient;
import org.isbel8ai.training.clinic.model.Role;
import org.isbel8ai.training.clinic.repository.PatientRepository;
import org.isbel8ai.training.clinic.rest.dto.NewPatientRequest;
import org.isbel8ai.training.clinic.service.AccountService;
import org.isbel8ai.training.clinic.service.PatientService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final AccountService accountService;

    private final PatientRepository patientRepository;

    @Override
    public Patient createPatient(NewPatientRequest newPatientRequest) {
        Account account = Account.builder()
                .fullName(newPatientRequest.fullName())
                .email(newPatientRequest.email())
                .password(RandomStringUtils.random(16))
                .role(Role.PATIENT)
                .build();

        Patient patient = Patient.builder()
                .account(accountService.createAccount(account))
                .build();

        return patientRepository.save(patient);
    }

    @Override
    public Patient getPatient(Long patientId) {
        return patientRepository.findById(patientId).orElseThrow();
    }

    @Override
    public Patient getPatientByEmail(String email) {
        return patientRepository.findByAccountEmail(email).orElseThrow();
    }
}
