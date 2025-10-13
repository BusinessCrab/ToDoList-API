package com.business_crab.ToDoList.API.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.business_crab.ToDoList.API.entities.Task;
import com.business_crab.ToDoList.API.repositories.TaskRepository;

import jakarta.transaction.Transactional;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    public TaskService(final TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    public Task create(final @RequestBody Task task) {
        return taskRepository.save(task);
    }

    @Transactional
    public void update(final @PathVariable Long id ,
                       final String title ,
                       final String description) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (optionalTask.isEmpty()) {
            throw new IllegalStateException("The user with such id is not found");
        }

        final Task task = optionalTask.get();
        
        if (title != null && !title.equals(task.getTitle())) {
            task.setTitle(title);
        }

        if (description != null && !description.equals(task.getDesc())) {
            task.setDesc(description);
        }
    }
    
    public void delete(final @PathVariable Long id) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (optionalTask.isEmpty()) {
            throw new IllegalStateException("Task with such id is not found");
        }
        taskRepository.deleteById(id);
    }
}