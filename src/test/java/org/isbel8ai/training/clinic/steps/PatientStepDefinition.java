package org.isbel8ai.training.clinic.steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import lombok.RequiredArgsConstructor;
import org.isbel8ai.training.clinic.rest.dto.NewPatientRequest;
import org.isbel8ai.training.clinic.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;


@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PatientStepDefinition {

    private final PatientService patientService;

    @Given("there are registered patients")
    public void patientsAreRegistered(DataTable dataTable) {
        dataTable.asList(NewPatientRequest.class).forEach(patientService::createPatient);
    }
}
