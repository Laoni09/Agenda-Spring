package com.example.agenda.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.agenda.domain.Contato.Contato;

@Repository
public interface ContatoRepository {
    // O Spring percebe que 'Usuario' é um objeto e acede ao 'id' dele para a query
    List<Contato> findByUsuarioId(Integer usuarioId);
}
