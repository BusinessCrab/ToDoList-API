package com.business_crab.ToDoList.API.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.business_crab.ToDoList.API.entities.Task;
import com.business_crab.ToDoList.API.services.TaskService;

@RestController
@RequestMapping(path = "api/tasks")
public class TaskContoller {
    private final TaskService taskService;

    public TaskContoller(final TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public List<Task> findAll() {
        return taskService.findAll();
    }

    @PostMapping
    public Task create(final @RequestBody Task task) {
        return taskService.create(task);
    }

    @PutMapping(path = "{id}")
    public void update(final @PathVariable Long id ,
                       final @RequestParam String title ,
                       final @RequestParam String description) {
        taskService.update(id , title , description);
    }

    @DeleteMapping(path = "{id}")
    public void delete(final @PathVariable Long id) {
        taskService.delete(id);
    }
}