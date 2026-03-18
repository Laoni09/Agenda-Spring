package com.example.agenda.domain.Task;

public record TaskDTO(
    Integer id,
    String nome,
    String description,
    boolean completed,
    Integer contatoId) {
}
