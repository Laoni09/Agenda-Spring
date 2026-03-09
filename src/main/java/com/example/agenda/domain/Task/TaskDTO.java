package com.example.agenda.domain.Task;

import com.example.agenda.domain.Contato.Contato;

public record TaskDTO(
    Integer id,
    String nome,
    String description,
    boolean completed,
    Contato contato) {
}
