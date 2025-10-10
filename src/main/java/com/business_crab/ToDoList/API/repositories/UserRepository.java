package com.business_crab.ToDoList.API.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.business_crab.ToDoList.API.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
}
