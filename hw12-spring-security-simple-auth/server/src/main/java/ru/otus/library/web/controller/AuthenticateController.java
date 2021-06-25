package ru.otus.library.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticateController {

    @PostMapping("/authenticate")
    public ResponseEntity<Void> authenticate() {
        return ResponseEntity.ok().build();
    }
}
