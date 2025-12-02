package org.isbel8ai.training.clinic.steps;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.DefaultDataTableCellTransformer;
import io.cucumber.java.DefaultDataTableEntryTransformer;
import io.cucumber.java.DefaultParameterTransformer;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.lang3.RandomStringUtils;
import org.assertj.core.api.Assertions;
import org.isbel8ai.training.clinic.model.Account;
import org.isbel8ai.training.clinic.model.Role;
import org.isbel8ai.training.clinic.repository.AccountRepository;
import org.isbel8ai.training.clinic.repository.PatientRepository;
import org.isbel8ai.training.clinic.rest.dto.AuthenticationResponse;
import org.isbel8ai.training.clinic.service.JwtTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.client.ExchangeResult;
import org.springframework.test.web.servlet.client.RestTestClient;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Type;
import java.util.Map;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CommonStepDefinition {

    private final RestTestClient restTestClient;

    private final JwtTokenService jwtService;

    private final AccountRepository accountRepository;

    private final PatientRepository patientRepository;

    private final EntityManager entityManager;

    private String token = null;

    private ExchangeResult lastResponse;


    @DefaultParameterTransformer
    @DefaultDataTableCellTransformer
    @DefaultDataTableEntryTransformer
    public Object transformer(Object fromValue, Type toValueType) {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.convertValue(fromValue, objectMapper.constructType(toValueType));
    }

    @Given("the application is running")
    @Transactional
    public void initializeData() {
        token = null;
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
                        .password(RandomStringUtils.insecure().next(16))
                        .role(Role.ADMIN)
                        .build()
        );
    }

    @Given("the client is authenticated as {string}")
    public void authenticate(String username) {
        token = jwtService.generateUserToken(username);
    }

    @SneakyThrows
    @Given("the client is authenticated with requested token")
    public void theClientIsAuthenticatedWithLastResponse() {
        ObjectMapper objectMapper = new ObjectMapper();
        token = objectMapper.readValue(lastResponse.getResponseBodyContent(), AuthenticationResponse.class).accessToken();
    }

    @Given("the client is not authenticated")
    public void theClientIsNotAuthenticated() {
        token = null;
    }

    @When("the client makes a GET request to {string}")
    public void theClientMakesAGETRequestTo(String path) {
        lastResponse = restTestClient.get().uri(path)
                .headers(this::setAuthentication)
                .exchange()
                .returnResult();
    }

    @When("the client makes a POST request to {string}")
    public void theClientMakesAPOSTRequestTo(String path, DataTable dataTable) {
        lastResponse = restTestClient.post().uri(path)
                .headers(this::setAuthentication)
                .body(dataTable.asMap())
                .exchange()
                .returnResult();
    }

    @When("the client makes a PATCH request to {string}")
    public void theClientMakesAPATCHRequestTo(String path, DataTable dataTable) {
        lastResponse = restTestClient.patch().uri(path)
                .headers(this::setAuthentication)
                .body(dataTable.asMap())
                .exchange()
                .returnResult();
    }

    @When("the client makes a PUT request to {string}")
    public void theClientMakesAPUTRequestTo(String path, DataTable dataTable) {
        lastResponse = restTestClient.put().uri(path)
                .headers(this::setAuthentication)
                .body(dataTable.asMap())
                .exchange()
                .returnResult();
    }

    @Then("the response status code should be {int}")
    public void theResponseCodeShouldBe(int statusCode) {
        Assertions.assertThat(lastResponse.getStatus().value()).isEqualTo(statusCode);
    }

    @SneakyThrows
    @Then("the response should contain")
    public void theResponseShouldContain(DataTable dataTable) {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> result = objectMapper.readValue(lastResponse.getResponseBodyContent(), new TypeReference<>() {});
        Map<String, Object> expected = dataTable.asMap(String.class, Object.class);
        Assertions.assertThat(result.entrySet()).containsAll(expected.entrySet());
    }

    private void setAuthentication(HttpHeaders headers) {
        if (token != null) headers.setBearerAuth(token);
    }
}

