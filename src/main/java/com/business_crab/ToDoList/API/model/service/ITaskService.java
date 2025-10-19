package com.business_crab.ToDoList.API.model.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.business_crab.ToDoList.API.model.entity.Task;

public interface ITaskService {
    public List<Task> getAll();
    public Task save(final Task task);
    public void deleteById(final Long id);
    public Task getById(final Long id);
    public Task update(final Task task);
    public Page<Task> getFilteredAndSortedTasks(final String title ,
                                                final Boolean isCompleted ,
                                                final String sortBy ,
                                                final String direction ,
                                                final int page ,
                                                final int limit);
}