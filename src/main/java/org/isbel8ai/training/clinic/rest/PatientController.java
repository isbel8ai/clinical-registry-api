package org.isbel8ai.training.clinic.rest;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.PropertyValueException;
import org.isbel8ai.training.clinic.rest.dto.NewPatientRequest;
import org.isbel8ai.training.clinic.rest.dto.PatientInfo;
import org.isbel8ai.training.clinic.service.PatientService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("patients")
@SecurityRequirement(name = "Authorization")
public class PatientController {

    private final PatientService patientService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PatientInfo createPatient(@RequestBody NewPatientRequest patientRequest) {
        return new PatientInfo(patientService.createPatient(patientRequest));
    }

    @GetMapping
    public List<PatientInfo> getAllPatients() {
        return patientService.getAllPatients().stream().map(PatientInfo::new).toList();
    }

    @GetMapping("{patientId}")
    public PatientInfo getPatient(@PathVariable Long patientId) {
        return new PatientInfo(patientService.getPatient(patientId));
    }

    @GetMapping("me")
    public PatientInfo getCurrentPatient() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return new PatientInfo(patientService.getPatientByEmail(userDetails.getUsername()));
    }

    @ExceptionHandler({PropertyValueException.class, DataIntegrityViolationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    void handleBadRequests(RuntimeException e) {
        log.warn(e.getMessage());
    }

    @ExceptionHandler({NoSuchElementException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    void handleNotFoundExceptions(RuntimeException e) {
        log.warn(e.getMessage());
    }
}
