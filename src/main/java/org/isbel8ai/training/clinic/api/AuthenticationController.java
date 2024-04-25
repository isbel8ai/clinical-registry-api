package org.isbel8ai.training.clinic.api;

import lombok.RequiredArgsConstructor;
import org.isbel8ai.training.clinic.api.dto.AuthRequest;
import org.isbel8ai.training.clinic.api.dto.AuthResponse;
import org.isbel8ai.training.clinic.service.JwtService;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    @PostMapping(path = "token",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    AuthResponse getToken(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.username(), authRequest.password()));
        if (authentication.isAuthenticated()) {
            return new AuthResponse(jwtService.generateToken(authRequest.username()));
        } else {
            throw new UsernameNotFoundException("invalid user request !");
        }
    }
}
