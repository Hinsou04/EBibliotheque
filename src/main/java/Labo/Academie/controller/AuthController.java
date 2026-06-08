package Labo.Academie.controller;

import Labo.Academie.dto.AuthResponse;
import Labo.Academie.dto.LoginRequest;
import Labo.Academie.dto.RegisterRequest;
import Labo.Academie.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentifcation", description = "Connexion et inscription")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    @Operation(summary = "Connexion d'un utilisateur")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request){
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/register")
    @Operation(summary = "Inscription d'un nouveau membre")
    public ResponseEntity<AuthResponse>register(@Valid @RequestBody RegisterRequest request){
        return ResponseEntity.status(201).body(authService.register(request));
    }
}
