package org.isbel8ai.training.clinic.service.impl;

import lombok.RequiredArgsConstructor;
import org.isbel8ai.training.clinic.model.Account;
import org.isbel8ai.training.clinic.model.Role;
import org.isbel8ai.training.clinic.repository.AccountRepository;
import org.isbel8ai.training.clinic.service.AccountService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    @Override
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    @Override
    public Account getAccountById(long id) {
        return accountRepository.findById(id).orElseThrow();
    }

    @Override
    public Account getAccountByEmail(String email) {
        return accountRepository.findByEmail(email).orElseThrow();
    }

    @Override
    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public void updateAccountInfo(long id, String fullName, String email, Role role) {
        Account account = accountRepository.findById(id).orElseThrow();
        if (fullName != null && !fullName.isEmpty()) {
            account.setFullName(fullName);
        }
        if (email != null && !email.isEmpty()) {
            account.setEmail(email);
        }
        if (role != null) {
            account.setRole(role);
        }
        accountRepository.save(account);
    }

    @Override
    public void updateAccountPassword(long id, String currentPassword, String newPassword) {
        Account account = accountRepository.findById(id).orElseThrow();
        if (account.getPassword().equals(currentPassword) && newPassword != null && !newPassword.isEmpty()) {
            account.setPassword(newPassword);
        }
        accountRepository.save(account);
    }

    @Override
    public void setAccountPassword(long id, String newPassword) {
        Account account = accountRepository.findById(id).orElseThrow();
        if (newPassword != null && !newPassword.isEmpty()) {
            account.setPassword(newPassword);
        }
        accountRepository.save(account);
    }

    @Override
    public void deleteAccount(long id) {
        accountRepository.deleteById(id);
    }
}
