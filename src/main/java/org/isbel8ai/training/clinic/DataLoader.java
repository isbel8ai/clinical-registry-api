package org.isbel8ai.training.clinic;

import lombok.RequiredArgsConstructor;
import org.isbel8ai.training.clinic.model.Account;
import org.isbel8ai.training.clinic.model.Role;
import org.isbel8ai.training.clinic.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataLoader implements ApplicationRunner {

    private static final String ADMIN_EMAIL = "admin@clinic.org";

    private final AccountRepository accountRepository;

    @Value("${spring.security.user.name}")
    private String adminUsername;

    @Value("${spring.security.user.password}")
    private String adminPassword;

    @Override
    public void run(ApplicationArguments args) {
        if (accountRepository.existsByEmail(ADMIN_EMAIL)) return;
        accountRepository.save(
                Account.builder()
                        .fullName("Admin")
                        .email(adminUsername)
                        .password(adminPassword)
                        .role(Role.ADMIN)
                        .build());
    }
}
