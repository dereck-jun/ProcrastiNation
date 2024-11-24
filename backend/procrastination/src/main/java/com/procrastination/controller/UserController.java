package com.procrastination.controller;

import com.procrastination.domain.user.User;
import com.procrastination.domain.user.UserAuthenticationRequest;
import com.procrastination.domain.user.UserAuthenticationResponse;
import com.procrastination.domain.user.UserRegisterRequest;
import com.procrastination.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<User> registration(@Valid @RequestBody UserRegisterRequest request) {
        return ResponseEntity.ok(userService.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<UserAuthenticationResponse> authenticate(
            @Valid @RequestBody UserAuthenticationRequest request) {
        return ResponseEntity.ok(userService.authenticate(request));
    }
}
