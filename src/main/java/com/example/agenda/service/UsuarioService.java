package com.example.agenda.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.agenda.domain.Usuario.Usuario;
import com.example.agenda.repository.UsuarioRepository;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Usuario login(String email, String senha) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        
        if (!passwordEncoder.matches(senha, usuario.getSenha())) {
            throw new RuntimeException("Senha incorreta");
        }

        return usuario;
    }

    public void register(String nome, String email, String senha) {
        if (usuarioRepository.existsByEmail(email)) {
            throw new RuntimeException("Email já registrado");
        }
        
        usuarioRepository.save(new Usuario(nome, email, passwordEncoder.encode(senha)));
    }
}
