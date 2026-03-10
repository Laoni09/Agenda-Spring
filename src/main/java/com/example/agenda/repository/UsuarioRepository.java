package com.example.agenda.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.agenda.domain.Usuario.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    // O Spring gera o SQL automático: SELECT * FROM usuarios WHERE email = ? AND senha = ?
    Optional<Usuario> findByEmailAndSenha(String email, String senha);

    boolean existsByEmail(String email);
}
