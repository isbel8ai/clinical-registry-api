package org.isbel8ai.training.clinic;

import io.cucumber.spring.CucumberContextConfiguration;
import org.isbel8ai.training.clinic.service.EmailService;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CucumberContextConfig {

    @MockBean
    EmailService emailService;
}
