package org.isbel8ai.training.clinic;

import io.cucumber.spring.CucumberContextConfiguration;
import org.isbel8ai.training.clinic.service.EmailService;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@AutoConfigureRestTestClient
@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CucumberContextConfig {

    @MockitoBean
    EmailService emailService;
}

