package com.umutyenidil.librarymanagement.auth;

import com.umutyenidil.librarymanagement.security.JwtService;
import com.umutyenidil.librarymanagement.user.User;
import com.umutyenidil.librarymanagement.user.UserRepository;
import com.umutyenidil.librarymanagement.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthRepository authRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final AuthMapper authMapper;
    private final UserService userService;

    @Transactional
    public UUID registerPatron(RegisterRequest request) {

        // eger e-posta daha once kullanilmissa hata firlat
        if (authRepository.findByEmail(request.email()).isPresent()) throw new EmailAlreadyExistsException();


        // yeni auth nesnesi olustur ve veritabanina kaydet
        var auth = Auth.builder()
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(Auth.Role.PATRON)
                .status(Auth.Status.ACTIVE)
                .build();
        var savedAuth = authRepository.save(auth);

        // user servisi ile kullanicinin bilgilerini kaydet
        userService.saveUser(
                User.builder()
                        .id(savedAuth.getId())
                        .name(request.userDetail().name())
                        .surname(request.userDetail().surname())
                        .phone(request.userDetail().phone())
                        .fullAddress(request.userDetail().fullAddress())
                        .build()
        );

        // kaydedilen kullanicinin id'sini dondur
        return savedAuth.getId();
    }

    @Transactional
    public UUID registerLibrarian(RegisterRequest request) {

        // eger e-posta daha once kullanilmissa hata firlat
        if (authRepository.findByEmail(request.email()).isPresent()) throw new EmailAlreadyExistsException();

        // yeni auth nesnesi olustur ve veritabanina kaydet
        var auth = Auth.builder()
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(Auth.Role.LIBRARIAN)
                .status(Auth.Status.ACTIVE)
                .build();
        var savedAuth = authRepository.save(auth);

        // user servisi ile kullanicinin bilgilerini kaydet
        userService.saveUser(
                User.builder()
                        .id(savedAuth.getId())
                        .name(request.userDetail().name())
                        .surname(request.userDetail().surname())
                        .build()
        );

        // kaydedilen kullanicinin id'sini dondur
        return savedAuth.getId();
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

    public boolean existsPatronById(UUID id) {

        return authRepository.existsByIdAndRoleAndStatus(id, Auth.Role.PATRON, Auth.Status.ACTIVE);
    }
}
