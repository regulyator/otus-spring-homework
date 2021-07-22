package ru.otus.library.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

    @GetMapping("/library/api/authenticate")
    public ResponseEntity<Void> authenticate() {
        return ResponseEntity.ok().build();
    }
}
