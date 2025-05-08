package com.umutyenidil.librarymanagement.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthRepository authRepository;
    private final PasswordEncoder passwordEncoder;

    public void register(RegisterRequest request) {

        var auth = Auth.builder()
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(Auth.Role.PATRON)
                .status(Auth.Status.ACTIVE)
                .build();

        authRepository.save(auth);
    }
}
