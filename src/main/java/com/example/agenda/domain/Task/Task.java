package com.example.agenda.domain.Task;

import com.example.agenda.domain.Contato.Contato;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 255)
    private String nome;

    @Column(nullable = false, length = 255)
    private String description;

    @Column(nullable = false)
    private boolean completed;

    @ManyToOne
    @JoinColumn(name = "contato_id", nullable = false)
    private Contato contato;

    public Task() {
    }

    public Task(String nome, String description, Contato contato) {
        this.nome = nome;
        this.description = description;
        this.completed = false;
        this.contato = contato;
    }

    public Task(Integer id, String nome, String description, boolean completed, Contato contato) {
        this.id = id;
        this.nome = nome;
        this.description = description;
        this.completed = completed;
        this.contato = contato;
    }

    public Integer getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getDescription() {
        return description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public Contato getContato() {
        return contato;
    }
}
