package com.example.agenda.domain.Contato;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;

import com.example.agenda.domain.Usuario.Usuario;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;

@Entity
@DiscriminatorValue("PESSOAL")
public class ContatoPessoal extends Contato {
    // vamos ver como colocar o not null aqui, porque o hibernate não aceita
    @NotBlank(message = "O CPF é obrigatório")
    @Column(length = 14)
    private String cpf;

    public ContatoPessoal() {
    }

    public ContatoPessoal(String nome, String telefone, String cpf, Usuario usuario) {
        super(nome, telefone, usuario);
        this.cpf = cpf;
    }

    public ContatoPessoal(Integer id, String nome, String telefone, String cpf, Usuario usuario) {
        super(id, nome, telefone, usuario);
        this.cpf = cpf;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
}
