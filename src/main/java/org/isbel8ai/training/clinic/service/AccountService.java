package org.isbel8ai.training.clinic.service;

import org.isbel8ai.training.clinic.model.Account;
import org.isbel8ai.training.clinic.rest.dto.PasswordUpdateRequest;

public interface AccountService {

    Account getAccount(long id);

    Account getAccountByEmail(String email);

    Account createAccount(Account account);

    void updateAccountPassword(String email, PasswordUpdateRequest passwordUpdateRequest);

    void setAccountPassword(long id, String newPassword);

    void deleteAccount(long id);
}
