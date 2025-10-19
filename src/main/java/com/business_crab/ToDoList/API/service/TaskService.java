package com.business_crab.ToDoList.API.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.business_crab.ToDoList.API.model.entity.Task;
import com.business_crab.ToDoList.API.model.entity.User;
import com.business_crab.ToDoList.API.model.service.ITaskService;
import com.business_crab.ToDoList.API.repository.TaskRepository;
import com.business_crab.ToDoList.API.repository.UserRepository;

@Service
public class TaskService implements ITaskService{

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    
    public TaskService(final TaskRepository taskRepository ,
                       final UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    private User getAuthenticatedUser() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final String username = authentication.getName();
        return userRepository.findByUsername(username).orElse(null);
    }

    @Override
    public List<Task> getAll() {
        final User user = getAuthenticatedUser();
        if (user != null) {
            return taskRepository.findAll()
                                 .stream()
                                 .filter(task -> task.getUser().getId().equals(user.getId()))
                                 .collect(Collectors.toList());
        }
        return List.of();
    }

    @Override
    public Task save(final Task task) {
        final User user = getAuthenticatedUser();

        if (task.getId() != null) {
            task.setId(null);
        }

        if (user != null) {
            task.setUser(user);
            return taskRepository.save(task); 
        }

        return null;
    }

    @Override
    public void deleteById(final Long id) {
        final User user = getAuthenticatedUser();
        taskRepository.findById(id).ifPresent(task -> {
            if (task.getUser().getId().equals(user.getId())) {
                taskRepository.deleteById(id);
            }
        });
    }

    @Override
    public Task getById(final Long id) {
        final User user = getAuthenticatedUser();
        return taskRepository.findById(id)
                             .filter(task -> task.getUser().getId().equals(user.getId()))
                             .orElse(null);
    }

    @Override
    public Task update(final Task task) {
        final User user = getAuthenticatedUser();
        if (user != null && taskRepository.findById(task.getId()).isPresent()) {
            task.setUser(user);
            return taskRepository.save(task);
        }
        return null;
    }

    @Override
    public Page<Task> getFilteredAndSortedTasks(final String title ,
                                                final Boolean isCompleted ,
                                                final String sortBy ,
                                                final String direction ,
                                                final int page ,
                                                final int limit) {
        final Sort sort = Sort.by(direction.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC , sortBy);
        final Pageable pageable = PageRequest.of(page , limit , sort);

        if (title != null && isCompleted != null) {
            return taskRepository.findByTitleContainingAndCompleted(title, isCompleted, pageable);
        } else if (title != null) {
            return taskRepository.findByTitleContaining(title, pageable);
        } else if (isCompleted != null) {
            return taskRepository.findByCompleted(isCompleted, pageable);
        } else {
            return taskRepository.findAll(pageable);
        }
    }
}
