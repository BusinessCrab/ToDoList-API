package com.business_crab.ToDoList.API.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
}
