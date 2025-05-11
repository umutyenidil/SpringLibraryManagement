package com.umutyenidil.librarymanagement.auth;

import com.umutyenidil.librarymanagement.common.dto.response.BaseResponse;
import com.umutyenidil.librarymanagement.common.dto.response.SuccessResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@RestController
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<?> register(
            @RequestBody @Valid RegisterRequest request
    ) {
        authService.registerPatron(request);

        return ResponseEntity.accepted().build();
    }

    @PostMapping("/login")
    public ResponseEntity<AuthTokenResponse> login(
            @RequestBody @Valid LoginRequest request
    ) {
        return ResponseEntity.ok(authService.login(request));
    }

    @GetMapping("/validate")
    public ResponseEntity<AuthResponse> validate(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String header
    ) {
        return ResponseEntity.ok(authService.validate(header));
    }

    @PostMapping("/register-librarian")
    public ResponseEntity<BaseResponse<UUID>> registerLibrarian(
        @RequestBody @Valid LibrarianRegisterRequest request
    ) {

        return ResponseEntity.ok(SuccessResponse.of(authService.registerLibrarian(request)));
    }
}
