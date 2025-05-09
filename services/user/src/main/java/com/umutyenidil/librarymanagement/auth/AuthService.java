package com.umutyenidil.librarymanagement.auth;

import com.umutyenidil.librarymanagement.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
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
    private final UserDetailsService userDetailsService;
    private final AuthMapper authMapper;

    public void registerPatron(RegisterRequest request) {
        if (authRepository.findByEmail(request.email()).isPresent()) throw new EmailAlreadyExistsException();

        var auth = Auth.builder()
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(Auth.Role.PATRON)
                .status(Auth.Status.ACTIVE)
                .build();

        authRepository.save(auth);
    }

    public void registerLibrarian(RegisterRequest request) {
        if (authRepository.findByEmail(request.email()).isPresent()) throw new EmailAlreadyExistsException();

        var auth = Auth.builder()
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(Auth.Role.LIBRARIAN)
                .status(Auth.Status.ACTIVE)
                .build();

        authRepository.save(auth);
    }

    public AuthTokenResponse login(LoginRequest request) {

        var authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );

        var claims = new HashMap<String, Object>();
        var auth = (Auth) authentication.getPrincipal();

        var accessToken = jwtService.generateToken(claims, auth);

        return AuthTokenResponse.builder()
                .accessToken(accessToken)
                .build();
    }

    public AuthResponse validate(String authorizationHeader) {

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer "))
            throw new UnauthorizedException();

        String jwt = authorizationHeader.substring(7);
        String email = jwtService.extractEmail(jwt);

        if (email == null) throw new UnauthorizedException();

        UserDetails userDetails = userDetailsService.loadUserByUsername(email);

        if (!jwtService.isTokenValid(jwt, userDetails)) throw new UnauthorizedException();

        var auth = authRepository.findByEmail(email)
                .orElseThrow(UnauthorizedException::new);

        return authMapper.toAuthResponse(auth);
    }
}
