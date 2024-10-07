package org.isbel8ai.training.clinic.rest.dto;

import org.isbel8ai.training.clinic.model.Account;
import org.isbel8ai.training.clinic.model.Role;

public record NewAccountDetails(
        String fullName,
        String email,
        String password,
        Role role
) {
    public Account toAccount() {
        return Account.builder().fullName(fullName).email(email).password(password).role(role).build();
    }
}
