package com.example.agenda.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

import com.example.agenda.domain.Contato.ContatoDTO;
import com.example.agenda.domain.Usuario.Usuario;
import com.example.agenda.service.AgendaService;

@RestController
@RequestMapping("/api/agenda")
public class AgendaController {
    private final AgendaService agendaService;

    public AgendaController(AgendaService agendaService) {
        this.agendaService = agendaService;
    }

    public record ContatoProfissionalRequest(
        @NotBlank(message = "Nome é obrigatório") String nome, 
        @NotBlank(message = "Telefone é obrigatório") String telefone, 
        @NotBlank(message = "Empresa é obrigatória") String empresa) {}
    
    public record ContatoPessoalRequest(
        @NotBlank(message = "Nome é obrigatório") String nome, 
        @NotBlank(message = "Telefone é obrigatório") String telefone, 
        @NotBlank(message = "CPF é obrigatório") String cpf) {}

    @GetMapping
    public ResponseEntity<List<ContatoDTO>> listarContatos(@AuthenticationPrincipal Usuario usuario) {
        List<ContatoDTO> contatos = agendaService.listarContatos(usuario.getId());
        return ResponseEntity.ok(contatos);
    }
    
    @PostMapping("/profissional")
    public ResponseEntity<Void> adicionarContatoProfissional(
            @AuthenticationPrincipal Usuario usuario, 
            @Valid @RequestBody ContatoProfissionalRequest request) {

        agendaService.adicionarContatoProfissional(request.nome(), request.telefone(), request.empresa(), usuario.getId());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/pessoal")
    public ResponseEntity<Void> adicionarContatoPessoal(
            @AuthenticationPrincipal Usuario usuario, 
            @Valid @RequestBody ContatoPessoalRequest request) {

        agendaService.adicionarContatoPessoal(request.nome(), request.telefone(), request.cpf(), usuario.getId());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/remover/{idContato}")
    public ResponseEntity<Void> removerContato(
            @AuthenticationPrincipal Usuario usuario, 
            @PathVariable Integer idContato) {
                
        agendaService.removerContato(idContato, usuario.getId());
        return ResponseEntity.noContent().build();
    }
}
