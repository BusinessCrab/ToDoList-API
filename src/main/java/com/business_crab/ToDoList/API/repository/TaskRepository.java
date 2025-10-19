package com.business_crab.ToDoList.API.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.business_crab.ToDoList.API.model.entity.Task;

@Repository
public interface  TaskRepository extends JpaRepository<Task, Long>{
    Page<Task> findByTitleContaining(final String title , final Pageable pageable);
    Page<Task> findByCompleted(final Boolean completed , final Pageable pageable);
    Page<Task> findByTitleContainingAndCompleted(final String title ,
                                                 final Boolean completed ,
                                                 final Pageable pageable);
}
