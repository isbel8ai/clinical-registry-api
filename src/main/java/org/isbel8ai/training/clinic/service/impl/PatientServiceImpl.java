package org.isbel8ai.training.clinic.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.isbel8ai.training.clinic.model.Account;
import org.isbel8ai.training.clinic.model.Patient;
import org.isbel8ai.training.clinic.model.Role;
import org.isbel8ai.training.clinic.repository.PatientRepository;
import org.isbel8ai.training.clinic.service.AccountService;
import org.isbel8ai.training.clinic.service.PatientService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final AccountService accountService;

    private final PatientRepository patientRepository;

    @Override
    public Patient createPatient(String fullName, String email) {
        Account account = Account.builder()
                .fullName(fullName)
                .email(email)
                .role(Role.PATIENT)
                .password(RandomStringUtils.random(16))
                .build();
        accountService.createAccount(account);
        return patientRepository.save(Patient.builder().account(account).build());
    }

    @Override
    public Patient getPatientById(Long patientId) {
        return patientRepository.findById(patientId).orElseThrow();
    }
}
