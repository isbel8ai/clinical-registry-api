package org.isbel8ai.training.clinic.api.dto;

import org.isbel8ai.training.clinic.model.Account;
import org.isbel8ai.training.clinic.model.Role;

public record AccountInfo(
        Long id,
        String fullName,
        String email,
        Role role
) {
    public AccountInfo(Account account) {
        this(account.getId(), account.getFullName(), account.getEmail(), account.getRole());
    }
}
