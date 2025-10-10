package com.business_crab.ToDoList.API.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.business_crab.ToDoList.API.entities.Task;
import com.business_crab.ToDoList.API.repositories.TaskRepository;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    public TaskService(final TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> findAll() {
        return taskRepository.findAll();
    }
}