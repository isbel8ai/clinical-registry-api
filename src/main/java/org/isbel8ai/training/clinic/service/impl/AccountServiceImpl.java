package org.isbel8ai.training.clinic.service.impl;

import lombok.RequiredArgsConstructor;
import org.isbel8ai.training.clinic.exception.AccountPasswordNotValidException;
import org.isbel8ai.training.clinic.model.Account;
import org.isbel8ai.training.clinic.repository.AccountRepository;
import org.isbel8ai.training.clinic.rest.dto.PasswordUpdateRequest;
import org.isbel8ai.training.clinic.service.AccountService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    @Override
    public Account getAccount(long id) {
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
    public void updateAccountPassword(String email, PasswordUpdateRequest passwordUpdateRequest) {
        Account account = accountRepository.findByEmail(email).orElseThrow();
        if (!account.getPassword().equals(passwordUpdateRequest.oldPassword())) {
            throw new AccountPasswordNotValidException();
        }
        account.setPassword(passwordUpdateRequest.newPassword());
        accountRepository.save(account);
    }

    @Override
    public void setAccountPassword(long id, String newPassword) {
        Account account = accountRepository.findById(id).orElseThrow();
        account.setPassword(newPassword);
        accountRepository.save(account);
    }

    @Override
    public void deleteAccount(long id) {
        accountRepository.deleteById(id);
    }
}
