package com.example.agenda.domain.Contato;

public record ContatoPessoalDTO(
    Integer id,
    String nome,
    String telefone,
    String cpf
) implements ContatoDTO {
}
