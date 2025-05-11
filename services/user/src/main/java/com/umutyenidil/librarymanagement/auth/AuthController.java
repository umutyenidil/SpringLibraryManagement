package com.umutyenidil.librarymanagement.auth;

import com.umutyenidil.librarymanagement.common.dto.response.BaseResponse;
import com.umutyenidil.librarymanagement.common.dto.response.SuccessResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@RestController
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register-patron")
    public ResponseEntity<BaseResponse<UUID>> registerPatron(
            @RequestBody @Validated(PatronValidationGroup.class) RegisterRequest request
    ) {

        return ResponseEntity.ok(SuccessResponse.of(authService.registerPatron(request)));
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
        @RequestBody @Validated(LibrarianValidationGroup.class) RegisterRequest request
    ) {

        return ResponseEntity.ok(SuccessResponse.of(authService.registerLibrarian(request)));
    }
}
