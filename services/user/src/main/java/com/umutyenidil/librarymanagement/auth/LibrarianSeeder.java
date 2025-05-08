package com.umutyenidil.librarymanagement.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
public class LibrarianSeeder implements CommandLineRunner {

    final AuthService authService;

    @Override
    public void run(String... args) throws Exception {

        try {
            var umut = RegisterRequest.builder()
                    .email("umut@librarymanagement.com")
                    .password("umut")
                    .build();

            authService.registerLibrarian(umut);
        } catch (EmailAlreadyExistsException e) {
            log.warn("Librarian already exists");
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
