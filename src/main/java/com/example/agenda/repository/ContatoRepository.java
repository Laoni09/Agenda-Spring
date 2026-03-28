package com.example.agenda.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.agenda.domain.Contato.Contato;

@Repository
public interface ContatoRepository extends JpaRepository<Contato, Integer> {
    // O Spring percebe que 'Usuario' é um objeto e acede ao 'id' dele para a query
    List<Contato> findByUsuarioId(Integer usuarioId);

    boolean existsByIdAndUsuarioId(Integer id, Integer usuarioId);
}
