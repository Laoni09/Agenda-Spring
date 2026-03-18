package com.example.agenda.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.agenda.domain.Contato.Contato;
import com.example.agenda.domain.Task.Task;
import com.example.agenda.domain.Task.TaskDTO;
import com.example.agenda.repository.ContatoRepository;
import com.example.agenda.repository.TaskRepository;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final ContatoRepository contatoRepository;
    
    public TaskService(TaskRepository taskRepository, ContatoRepository contatoRepository) {
        this.taskRepository = taskRepository;
        this.contatoRepository = contatoRepository;
    }

    @Transactional
    public void adicionarTask(String nome, String description, Integer contatoId) {
        Contato contato = contatoRepository.getReferenceById(contatoId);
        taskRepository.save(new Task(nome, description, contato));
    }

    public List<TaskDTO> listarTasks(Integer contatoId) {
        return taskRepository.findByContatoId(contatoId).stream()
                .map(task -> new TaskDTO(task.getId(), task.getNome(), task.getDescription(), task.isCompleted(), task.getContato().getId()))
                .toList();
    }

    @Transactional
    public void atualizarTask(Integer id, String nome, String description, boolean completed, Integer contatoId) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task não encontrada."));
            
        if (!task.getContato().getId().equals(contatoId)) {
            throw new RuntimeException("Acesso negado.");
        }

        task.setNome(nome);
        task.setDescription(description);
        task.setCompleted(completed);
        taskRepository.save(task);
    }

    @Transactional
    public void removerTask(Integer id, Integer contatoId) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task não encontrada."));

        if (!task.getContato().getId().equals(contatoId)) {
            throw new RuntimeException("Acesso negado.");
        }
        
        taskRepository.delete(task);
    }
}