package org.isbel8ai.training.clinic.service;

import org.isbel8ai.training.clinic.model.Account;
import org.isbel8ai.training.clinic.model.Role;

import java.util.List;

public interface AccountService {

    List<Account> getAllAccounts();

    Account getAccountById(long id);

    Account getAccountByEmail(String email);

    Account addAccount(Account account);

    void updateAccountInfo(long id, String fullName, String email, Role role);

    void updateAccountPassword(long id, String currentPassword, String newPassword);

    void setAccountPassword(long id, String newPassword);

    void deleteAccount(long id);
}
