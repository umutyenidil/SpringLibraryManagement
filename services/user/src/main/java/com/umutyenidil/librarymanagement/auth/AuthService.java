package com.umutyenidil.librarymanagement.auth;

import com.umutyenidil.librarymanagement.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthRepository authRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public void register(RegisterRequest request) {

        var auth = Auth.builder()
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(Auth.Role.PATRON)
                .status(Auth.Status.ACTIVE)
                .build();

        authRepository.save(auth);
    }

    public AuthResponse login(LoginRequest request) {

        var authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );

        var claims = new HashMap<String, Object>();
        var auth = (Auth) authentication.getPrincipal();

        var accessToken = jwtService.generateToken(claims, auth);

        return AuthResponse.builder()
                .accessToken(accessToken)
                .build();
    }
}
