package com.example.agenda.domain.Contato;

public record ContatoProfissionalDTO(
    Integer id,
    String nome,
    String telefone,
    String empresa
) implements ContatoDTO {
}
