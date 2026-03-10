package com.example.agenda.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.agenda.domain.Task.Task;

public interface TaskRepository extends JpaRepository<Task, Integer> {
    List<Task> findByContatoId(Integer contatoId);
}
