package com.business_crab.ToDoList.API.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.business_crab.ToDoList.API.model.dto.DataResponse;
import com.business_crab.ToDoList.API.model.entity.Task;
import com.business_crab.ToDoList.API.model.service.ITaskService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/api/tasks")
public class TaskContoller {
    private final ITaskService taskService;

    public TaskContoller(final ITaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping()
    public ResponseEntity<DataResponse<List<Task>>> getAll(
        final @RequestParam(required=false)  String title , final @RequestParam(required=false) Boolean isCompleted ,
        final @RequestParam(required=false) String sortBy , final @RequestParam(required=false) String direction ,
        final @RequestParam(required=false) int page , final @RequestParam(required=false) int limit
    ) {
        final DataResponse<List<Task>> response = new DataResponse<>();
        final Page<Task> taskPage;

        if (title == null && isCompleted == null) {
            taskPage = taskService.getFilteredAndSortedTasks(null, null, sortBy, direction, page, limit);
        } else {
            taskPage = taskService.getFilteredAndSortedTasks(title, isCompleted, sortBy, direction, page, limit);
        }

        response.setData(taskPage.getContent());
        response.setPage(taskPage.getNumber());
        response.setLimit(taskPage.getSize());
        response.setTotal(taskPage.getTotalElements());
        response.setMessage(HttpStatus.OK.toString());

        return ResponseEntity.ok(response);
    }

    @PostMapping()
    public ResponseEntity<DataResponse<Task>> save(final @RequestBody Task entity) {
        Task task = taskService.save(entity);
        DataResponse<Task> response = new DataResponse<>();
        if (task == null) {
            response.setMessage(HttpStatus.BAD_REQUEST.toString());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        response.setData(task);
        response.setMessage(HttpStatus.CREATED.toString());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DataResponse<Task>> getById(@PathVariable final Long id ,
                                                      @RequestBody final Task entity) {
        final Task task = taskService.getById(id);
        DataResponse<Task> response = new DataResponse<>();
        if (task == null) {
            response.setMessage(HttpStatus.BAD_REQUEST.toString());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        entity.setId(id);
        Task updatedTask = taskService.update(entity);
        response.setData(updatedTask);
        response.setMessage(HttpStatus.ACCEPTED.toString());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DataResponse<Task>> getById(final @PathVariable Long id) {
        Task task = taskService.getById(id);
        DataResponse<Task> response = new DataResponse<>();
        if (task == null) {
            response.setMessage(HttpStatus.NOT_FOUND.toString());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        response.setData(task);
        response.setMessage(HttpStatus.OK.toString());
        return ResponseEntity.ok(response);
    }
}