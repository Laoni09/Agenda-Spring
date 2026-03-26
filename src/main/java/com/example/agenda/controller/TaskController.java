package com.example.agenda.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

import com.example.agenda.domain.Task.TaskDTO;
import com.example.agenda.domain.Usuario.Usuario;
import com.example.agenda.service.TaskService;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    private final TaskService taskService;  

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    public record TaskRequest(
        @NotBlank(message = "Nome é obrigatório") String nome, 
        @NotBlank(message = "Descrição é obrigatória") String description, 
        @NotBlank(message = "Status é obrigatório") boolean completed) {}

    @GetMapping("/{contatoId}")
    public ResponseEntity<List<TaskDTO>> listarTasks(
            @AuthenticationPrincipal Usuario usuario,
            @PathVariable Integer contatoId) {
        return ResponseEntity.ok(taskService.listarTasks(contatoId, usuario.getId()));
    }

    @PostMapping("/{contatoId}")
    public ResponseEntity<Void> adicionarTask(
            @AuthenticationPrincipal Usuario usuario,
            @PathVariable Integer contatoId, 
            @Valid @RequestBody TaskRequest request) {
        taskService.adicionarTask(request.nome(), request.description(), contatoId, usuario.getId());
        return ResponseEntity.status(HttpStatus.CREATED).build();  
    }

    @PutMapping("/{contatoId}/{taskId}")
    public ResponseEntity<Void> atualizarTask(
            @AuthenticationPrincipal Usuario usuario,
            @PathVariable Integer contatoId, 
            @PathVariable Integer taskId, 
            @Valid @RequestBody TaskRequest request) {
        taskService.atualizarTask(taskId, request.nome(), request.description(), request.completed(), contatoId, usuario.getId());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{contatoId}/{taskId}")
    public ResponseEntity<Void> removerTask(
            @AuthenticationPrincipal Usuario usuario,
            @PathVariable Integer contatoId, 
            @PathVariable Integer taskId) {
        taskService.removerTask(taskId, contatoId, usuario.getId());
        return ResponseEntity.noContent().build();
    }
}
