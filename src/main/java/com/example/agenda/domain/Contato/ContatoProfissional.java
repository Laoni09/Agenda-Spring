package com.example.agenda.domain.Contato;

import com.example.agenda.domain.Usuario.Usuario;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;

@Entity
@DiscriminatorValue("PROFISSIONAL")
public class ContatoProfissional extends Contato {
    // vamos ver como colocar o not null aqui, porque o hibernate não aceita
    @NotBlank(message = "A empresa é obrigatória")
    @Column(length = 255)
    private String empresa;

    public ContatoProfissional() {
    }

    public ContatoProfissional(String nome, String telefone, String empresa, Usuario usuario) {
        super(nome, telefone, usuario);
        this.empresa = empresa;
    }

    public ContatoProfissional(Integer id, String nome, String telefone, String empresa, Usuario usuario) {
        super(id, nome, telefone, usuario);
        this.empresa = empresa;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }
}
