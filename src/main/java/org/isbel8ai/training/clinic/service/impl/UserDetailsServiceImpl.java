package org.isbel8ai.training.clinic.service.impl;

import lombok.RequiredArgsConstructor;
import org.isbel8ai.training.clinic.model.Account;
import org.isbel8ai.training.clinic.model.Role;
import org.isbel8ai.training.clinic.service.AccountService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final AccountService accountService;
    @Value("${spring.security.user.password}")
    private String adminPassword;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        if (username.equals("admin")) {
            return User.builder()
                    .username("admin")
                    .password(passwordEncoder.encode(adminPassword))
                    .roles(Role.DIRECTOR.name())
                    .build();
        }

        Account account = accountService.getAccountByEmail(username);
        return User.builder()
                .username(account.getEmail())
                .password(passwordEncoder.encode(account.getPassword()))
                .roles(account.getRole().name())
                .build();
    }
}
