package com.example.agenda.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.constraints.NotBlank;

import com.example.agenda.domain.Contato.ContatoDTO;
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

    @GetMapping("/{usuarioId}")
    public ResponseEntity<List<ContatoDTO>> listarContatos(@PathVariable Integer usuarioId) {
        List<ContatoDTO> contatos = agendaService.listarContatos(usuarioId);
        return ResponseEntity.ok(contatos);
    }
    
    @PostMapping("/{usuarioId}/profissional")
    public ResponseEntity<Void> adicionarContatoProfissional(@PathVariable Integer usuarioId, @RequestBody ContatoProfissionalRequest request) {
        agendaService.adicionarContatoProfissional(request.nome(), request.telefone(), request.empresa(), usuarioId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/{usuarioId}/pessoal")
    public ResponseEntity<Void> adicionarContatoPessoal(@PathVariable Integer usuarioId, @RequestBody ContatoPessoalRequest request) {
        agendaService.adicionarContatoPessoal(request.nome(), request.telefone(), request.cpf(), usuarioId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{usuarioId}/remover/{idContato}")
    public ResponseEntity<Void> removerContato(@PathVariable Integer usuarioId, @PathVariable Integer idContato) {
        agendaService.removerContato(idContato, usuarioId);
        return ResponseEntity.noContent().build();
    }
}
