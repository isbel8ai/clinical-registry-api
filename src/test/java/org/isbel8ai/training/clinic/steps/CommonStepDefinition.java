package org.isbel8ai.training.clinic.steps;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.DefaultDataTableCellTransformer;
import io.cucumber.java.DefaultDataTableEntryTransformer;
import io.cucumber.java.DefaultParameterTransformer;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.assertj.core.api.Assertions;
import org.isbel8ai.training.clinic.model.Account;
import org.isbel8ai.training.clinic.model.Role;
import org.isbel8ai.training.clinic.repository.AccountRepository;
import org.isbel8ai.training.clinic.repository.PatientRepository;
import org.isbel8ai.training.clinic.rest.dto.AuthenticationResponse;
import org.isbel8ai.training.clinic.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;


@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CommonStepDefinition {

    private final TestRestTemplate restTemplate;

    private final JwtService jwtService;

    private final AccountRepository accountRepository;

    private final PatientRepository patientRepository;

    private final EntityManager entityManager;

    private final ObjectMapper objectMapper;

    private ResponseEntity<String> lastResponse;

    @PostConstruct
    void setup() {
        restTemplate.getRestTemplate().setRequestFactory(new JdkClientHttpRequestFactory());
    }

    @DefaultParameterTransformer
    @DefaultDataTableCellTransformer
    @DefaultDataTableEntryTransformer
    public Object transformer(Object fromValue, Type toValueType) {
        return objectMapper.convertValue(fromValue, objectMapper.constructType(toValueType));
    }

    @Given("the application is running")
    @Transactional
    public void initializeData() {
        restTemplate.getRestTemplate().setInterceptors(List.of());
        patientRepository.deleteAll();
        accountRepository.deleteAll();
        entityManager.createNativeQuery("ALTER TABLE PATIENT ALTER COLUMN ID RESTART WITH 1").executeUpdate();
        entityManager.createNativeQuery("ALTER TABLE ACCOUNT ALTER COLUMN ID RESTART WITH 1").executeUpdate();
    }

    @Given("the system admin user is created")
    public void createSystemAdminUser() {
        accountRepository.save(
                Account.builder()
                        .email("admin")
                        .fullName("Administrator")
                        .password(RandomStringUtils.random(16))
                        .role(Role.ADMIN)
                        .build()
        );
    }

    @Given("the client is authenticated as {string}")
    public void authenticate(String username) {
        String token = jwtService.generateToken(username);
        configureRestTemplate(token);
    }

    @Given("the client is authenticated with requested token")
    public void theClientIsAuthenticatedWithLastResponse() throws JsonProcessingException {
        String token = objectMapper.readValue(lastResponse.getBody(), AuthenticationResponse.class).accessToken();
        configureRestTemplate(token);
    }

    @Given("the client is not authenticated")
    public void theClientIsNotAuthenticated() {
        restTemplate.getRestTemplate().setInterceptors(List.of());
    }

    @When("the client makes a GET request to {string}")
    public void theClientMakesAGETRequestTo(String path) {
        lastResponse = restTemplate.getForEntity(path, String.class);
    }

    @When("the client makes a POST request to {string}")
    public void theClientMakesAPOSTRequestTo(String path, DataTable dataTable) {
        lastResponse = restTemplate.postForEntity(path, dataTable.asMap(), String.class);
    }

    @When("the client makes a PATCH request to {string}")
    public void theClientMakesAPATCHRequestTo(String path, DataTable dataTable) {
        executeHttpRequest(path, HttpMethod.PATCH, dataTable);
    }

    @When("the client makes a PUT request to {string}")
    public void theClientMakesAPUTRequestTo(String path, DataTable dataTable) {
        executeHttpRequest(path, HttpMethod.PUT, dataTable);
    }

    @Then("the response status code should be {int}")
    public void theResponseCodeShouldBe(int statusCode) {
        Assertions.assertThat(lastResponse.getStatusCode().value()).isEqualTo(statusCode);
    }

    @Then("the response should contain")
    public void theResponseShouldContain(DataTable dataTable) throws JsonProcessingException {
        Map<String, Object> result = objectMapper.readValue(lastResponse.getBody(), new TypeReference<>() {});
        Map<String, Object> expected = dataTable.asMap(String.class, Object.class);
        Assertions.assertThat(result.entrySet()).containsAll(expected.entrySet());
    }

    private void configureRestTemplate(String token) {
        restTemplate.getRestTemplate().setInterceptors(List.of((request, body, execution) -> {
            request.getHeaders().setBearerAuth(token);
            return execution.execute(request, body);
        }));
    }

    private void executeHttpRequest(String path, HttpMethod httpMethod, DataTable dataTable) {
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(dataTable.asMap(String.class, Object.class));
        lastResponse = restTemplate.exchange(path, httpMethod, requestEntity, String.class);
    }
}

