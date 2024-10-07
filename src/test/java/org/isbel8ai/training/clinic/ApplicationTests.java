package org.isbel8ai.training.clinic;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration(classes = Application.class)
class ApplicationTests {

    @Test
    void contextLoads() {
        Assertions.assertTrue(true);
    }

}
