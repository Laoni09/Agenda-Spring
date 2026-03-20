package com.example.agenda.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.constraints.NotBlank;

import com.example.agenda.service.UsuarioService;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {
    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // DTOs de entrada
    public record LoginRequest(
        @NotBlank(message = "Email é obrigatório") String email, 
        @NotBlank(message = "Senha é obrigatória") String senha) {}
        
    public record RegisterRequest(
        @NotBlank(message = "Nome é obrigatório") String nome, 
        @NotBlank(message = "Email é obrigatório") String email, 
        @NotBlank(message = "Senha é obrigatória") String senha) {}

    @PostMapping("/login")
    public ResponseEntity<Integer> login(@RequestBody LoginRequest request) {
        Integer usuarioId = usuarioService.login(request.email(), request.senha());
        return ResponseEntity.ok(usuarioId); // Retorna HTTP 200 com o ID
    }

    @PostMapping("/registrar")
    public ResponseEntity<Void> registrar(@RequestBody RegisterRequest request) {
        usuarioService.register(request.nome(), request.email(), request.senha());
        return ResponseEntity.status(HttpStatus.CREATED).build(); // Retorna HTTP 201 (Created)
    }
}
