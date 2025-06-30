package com.ts.juridico.application.controller;

import com.ts.juridico.application.dto.request.AuthCreateUserRequestDto;
import com.ts.juridico.application.dto.request.AuthRequestDto;
import com.ts.juridico.application.dto.response.AuthCreateUserResponseDto;
import com.ts.juridico.application.dto.response.AuthResponseDto;
import com.ts.juridico.domain.model.Usuario;
import com.ts.juridico.domain.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequestDto authRequest) {
        AuthResponseDto token = authService.login(authRequest);

        if(token != null) {
            return ResponseEntity.ok(token);
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
    }

    @PostMapping("/login/create")
    public ResponseEntity<?> login(@RequestBody AuthCreateUserRequestDto authRequest) {
        Usuario user = authService.create(authRequest);

        if(user != null) {
            return ResponseEntity.ok(user);
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
    }

    @GetMapping("/users")
    public ResponseEntity<?> searchUsers() {
        List<AuthCreateUserResponseDto> all = authService.findAll();
        return ResponseEntity.ok(all);
    }
}
