package org.isbel8ai.training.clinic.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.isbel8ai.training.clinic.model.Account;
import org.isbel8ai.training.clinic.rest.dto.AuthenticationRequest;
import org.isbel8ai.training.clinic.rest.dto.AuthenticationResponse;
import org.isbel8ai.training.clinic.rest.dto.EmailDto;
import org.isbel8ai.training.clinic.rest.dto.PasswordResetRequest;
import org.isbel8ai.training.clinic.service.AccountService;
import org.isbel8ai.training.clinic.service.EmailService;
import org.isbel8ai.training.clinic.service.JwtTokenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@Slf4j
@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final JwtTokenService jwtTokenService;

    private final AccountService accountService;

    private final EmailService emailService;

    private final AuthenticationManager authenticationManager;

    @PostMapping(path = "token",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public AuthenticationResponse getToken(@Valid @RequestBody AuthenticationRequest authenticationRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authenticationRequest.username(),
                authenticationRequest.password()
        ));

        return new AuthenticationResponse(jwtTokenService.generateUserToken(authenticationRequest.username()));
    }

    @PostMapping(path = "/password/reset/email",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public void requestPasswordReset(@Valid @RequestBody EmailDto emailDto) {
        Account account = accountService.getAccountByEmail(emailDto.email());
        String resetToken = jwtTokenService.generatePasswordResetToken(account.getEmail());
        emailService.sendSimpleEmail(account.getEmail(), "Password Reset", resetToken);
    }

    @PostMapping(path = "/password/reset",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public void resetPassword(@Valid @RequestBody PasswordResetRequest passwordResetRequest) {
        if (jwtTokenService.isValidPasswordResetToken(passwordResetRequest.resetToken())) {
            String email = jwtTokenService.extractUsername(passwordResetRequest.resetToken());
            accountService.setAccountPassword(email, passwordResetRequest.newPassword());
        }
    }

    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    void handleNotFoundExceptions(RuntimeException e) {
        log.warn(e.getMessage());
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    void handleAuthenticationException(AuthenticationException e) {
        log.error(e.getMessage());
    }
}
