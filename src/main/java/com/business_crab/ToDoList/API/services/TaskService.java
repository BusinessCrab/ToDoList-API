package com.business_crab.ToDoList.API.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

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

    public Task create(final @RequestBody Task task) {
        return taskRepository.save(task);
    }

    public Task update(final @RequestBody Task newTask , final @PathVariable Long id) {
        return taskRepository.findById(id).map((task) -> {
            task.setTitle(newTask.getTitle());
            task.setDesc(newTask.getDesc());
            return taskRepository.save(task);
        }).orElseGet(() -> {
            return taskRepository.save(newTask);
        });
    }
    
    public void delete(final @PathVariable Long id) {
        taskRepository.deleteById(id);
    }
}