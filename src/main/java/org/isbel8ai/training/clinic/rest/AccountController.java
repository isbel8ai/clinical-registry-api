package org.isbel8ai.training.clinic.rest;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.isbel8ai.training.clinic.exception.AccountPasswordNotValidException;
import org.isbel8ai.training.clinic.rest.dto.PasswordSetRequest;
import org.isbel8ai.training.clinic.rest.dto.PasswordUpdateRequest;
import org.isbel8ai.training.clinic.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("accounts")
@SecurityRequirement(name = "Authorization")
public class AccountController {

    private final AccountService accountService;

    @PatchMapping(value = "me/password", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void updateAccountPassword(@Valid @RequestBody PasswordUpdateRequest passwordUpdateRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        accountService.updateAccountPassword(username, passwordUpdateRequest);
    }

    @PutMapping(value = "{accountId}/password", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void setAccountPassword(@PathVariable long accountId,
                                   @Valid @RequestBody PasswordSetRequest passwordSetRequest) {
        accountService.setAccountPassword(accountId, passwordSetRequest.newPassword());
    }

    @ExceptionHandler(AccountPasswordNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    void handleAccountPasswordNotValidException(AccountPasswordNotValidException e) {
        log.error(e.getMessage());
    }
}
