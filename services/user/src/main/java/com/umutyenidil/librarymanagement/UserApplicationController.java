package com.umutyenidil.librarymanagement;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/user-service")
@RestController
public class UserApplicationController {

    @GetMapping
    public ResponseEntity<?> index() {
        return ResponseEntity.ok("Houston, we are at user-service!");
    }
}
