package com.example.agenda.service;

import org.springframework.stereotype.Service;

import com.example.agenda.domain.Usuario.Usuario;
import com.example.agenda.repository.UsuarioRepository;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Integer login(String email, String senha) {
        return usuarioRepository.findByEmailAndSenha(email, senha)
                .map(usuario -> usuario.getId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    public void register(String nome, String email, String senha) {
        if (usuarioRepository.existsByEmail(email)) {
            throw new RuntimeException("Email já registrado");
        }
        usuarioRepository.save(new Usuario(nome, email, senha));
    }
}
