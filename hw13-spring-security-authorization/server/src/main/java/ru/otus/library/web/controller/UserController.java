package ru.otus.library.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.library.domain.User;
import ru.otus.library.service.data.UserService;

import java.util.Collection;

@RestController
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/library/api/users")
    public ResponseEntity<Collection<User>> getAllAuthors() {
        return ResponseEntity.ok(userService.getAll());
    }
}
