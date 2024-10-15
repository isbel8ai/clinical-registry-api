package org.isbel8ai.training.clinic.rest;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.isbel8ai.training.clinic.rest.dto.PatientInfo;
import org.isbel8ai.training.clinic.service.PatientService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("patients")
@SecurityRequirement(name = "Authorization")
public class PatientController {

    private final PatientService patientService;

    @PostMapping
    public PatientInfo createPatient(@RequestBody PatientInfo patientInfo) {
        return new PatientInfo(patientService.createPatient(patientInfo.fullName(), patientInfo.email()));
    }

    @GetMapping("{patientId}")
    public PatientInfo getPatient(@PathVariable Long patientId) {
        return new PatientInfo(patientService.getPatientById(patientId));
    }

}
