package org.isbel8ai.training.clinic.api;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.isbel8ai.training.clinic.api.dto.AccountInfo;
import org.isbel8ai.training.clinic.api.dto.NewAccountDetails;
import org.isbel8ai.training.clinic.api.dto.PasswordUpdate;
import org.isbel8ai.training.clinic.model.Account;
import org.isbel8ai.training.clinic.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("accounts")
@SecurityRequirement(name = "Authorization")
public class AccountController {

    private static final Logger log = LoggerFactory.getLogger(AccountController.class);

    private final AccountService accountService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<AccountInfo> getAllAccounts() {
        List<Account> list = accountService.getAllAccounts();
        return list.stream().map(AccountInfo::new).toList();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public AccountInfo createAccount(@RequestBody NewAccountDetails newAccountDetails) {
        log.info("Creating new worker: {}", newAccountDetails);
        return new AccountInfo(accountService.addAccount(newAccountDetails.toAccount()));
    }

    @GetMapping(value = "me", produces = MediaType.APPLICATION_JSON_VALUE)
    public AccountInfo getCurrentAccount() {
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        if (username == null) {
            throw new UsernameNotFoundException("No valid logged in user");
        }

        return new AccountInfo(accountService.getAccountByEmail(username));
    }

    @GetMapping(value = "{accountId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public AccountInfo getAccount(@PathVariable long accountId) {
        return new AccountInfo(accountService.getAccountById(accountId));
    }

    @PatchMapping(value = "{accountId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void updateAccountInfo(@PathVariable long accountId, @RequestBody AccountInfo accountInfo) {
        accountService.updateAccountInfo(accountId, accountInfo.fullName(), accountInfo.email(), accountInfo.role());
    }

    @PatchMapping(value = "{accountId}/password", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void updateAccountPassword(@PathVariable long accountId, @RequestBody PasswordUpdate passwordUpdate) {
        accountService.updateAccountPassword(accountId, passwordUpdate.currentPassword(), passwordUpdate.newPassword());
    }

    @PutMapping(value = "{accountId}/password", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void setAccountPassword(@PathVariable long accountId, @RequestBody PasswordUpdate passwordUpdate) {
        accountService.setAccountPassword(accountId, passwordUpdate.newPassword());
    }

}
