package com.example.agenda.repository;

import java.util.List;

import com.example.agenda.domain.Task.Task;

public interface TaskRepository {
    List<Task> findByContatoId(Integer contatoId);
}
