package ru.otus.library.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.library.configuration.security.DemoInitAcl;

@RestController
public class SecurityController {

    private final DemoInitAcl demoInitAcl;

    public SecurityController(DemoInitAcl demoInitAcl) {
        this.demoInitAcl = demoInitAcl;
    }

    @GetMapping("/library/api/initacl")
    public ResponseEntity<Void> initAcl() {
        demoInitAcl.initAcl();
        return ResponseEntity.ok().build();
    }
}
