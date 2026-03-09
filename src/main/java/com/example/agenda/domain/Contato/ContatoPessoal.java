package com.example.agenda.domain.Contato;

import jakarta.persistence.Entity;

import com.example.agenda.domain.Usuario.Usuario;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;

@Entity
@DiscriminatorValue("PESSOAL")
public class ContatoPessoal extends Contato {
    @Column(nullable = false, length = 14)
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
