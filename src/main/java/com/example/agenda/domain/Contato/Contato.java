package com.example.agenda.domain.Contato;

import com.example.agenda.domain.Task.Task;
import com.example.agenda.domain.Usuario.Usuario;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.Table;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;

@Entity
@Table(name = "contatos")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_contato")
public abstract class Contato {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    @Column(nullable = false, length = 100)
    protected String nome;

    @Column(nullable = false, length = 20)
    protected String telefone;

    @OneToMany(mappedBy = "contato", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private java.util.List<Task> tasks;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    public Contato() {
    }

    public Contato(String nome, String telefone, Usuario usuario) {
        this.nome = nome;
        this.telefone = telefone;
        this.usuario = usuario;
    }

    public Contato(Integer id, String nome, String telefone, Usuario usuario) {
        this.id = id;
        this.nome = nome;
        this.telefone = telefone;
        this.usuario = usuario;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Integer getId() {
        return id;
    }  

    public String getNome() {
        return nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
}