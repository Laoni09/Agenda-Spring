package com.example.agenda.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

import com.example.agenda.domain.Usuario.Usuario;
import com.example.agenda.service.TokenService;
import com.example.agenda.service.UsuarioService;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {
    private final UsuarioService usuarioService;
    private final TokenService tokenService;

    public UsuarioController(UsuarioService usuarioService, TokenService tokenService) {
        this.usuarioService = usuarioService;
        this.tokenService = tokenService;
        
    }

    // DTOs de entrada
    public record LoginRequest(
        @NotBlank(message = "Email é obrigatório") String email, 
        @NotBlank(message = "Senha é obrigatória") String senha) {}

    public record RegisterRequest(
        @NotBlank(message = "Nome é obrigatório") String nome, 
        @NotBlank(message = "Email é obrigatório") String email, 
        @NotBlank(message = "Senha é obrigatória") String senha) {}

    // DTO de resposta
    public record TokenResponse(String token) {}

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@Valid @RequestBody LoginRequest request) {

        Usuario usuario = usuarioService.login(request.email(), request.senha());

        String token = tokenService.generateToken(usuario);
        
        return ResponseEntity.ok(new TokenResponse(token)); // Retorna HTTP 200 com o token
    }

    @PostMapping("/registrar")
    public ResponseEntity<Void> registrar(@Valid @RequestBody RegisterRequest request) {
        usuarioService.register(request.nome(), request.email(), request.senha());
        return ResponseEntity.status(HttpStatus.CREATED).build(); // Retorna HTTP 201 (Created)
    }
}
