package org.isbel8ai.training.clinic;

import lombok.RequiredArgsConstructor;
import org.isbel8ai.training.clinic.model.Account;
import org.isbel8ai.training.clinic.model.Role;
import org.isbel8ai.training.clinic.repository.AccountRepository;
import org.isbel8ai.training.clinic.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataLoader implements ApplicationRunner {

    private static final String ADMIN_ROLE = "ADMIN";
    private static final String ADMIN_EMAIL = "admin@clinic.org";
    private static final String ADMIN_FULL_NAME = "Administrator";

    private final AccountRepository accountRepository;

    private final RoleRepository roleRepository;

    @Value("${spring.security.user.name}")
    private String adminUsername;

    @Value("${spring.security.user.password}")
    private String adminPassword;

    @Override
    public void run(ApplicationArguments args) {
        if (accountRepository.existsByEmail(ADMIN_EMAIL)) return;


        Role adminRole = roleRepository.findByName(ADMIN_ROLE)
                .orElse(roleRepository.save(Role.builder().name(ADMIN_ROLE).build()));

        accountRepository.save(
                Account.builder()
                        .fullName(ADMIN_FULL_NAME)
                        .email(adminUsername)
                        .password(adminPassword)
                        .role(adminRole)
                        .build());
    }
}
