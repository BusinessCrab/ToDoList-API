package com.business_crab.ToDoList.API.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.business_crab.ToDoList.API.model.entity.User;

@Repository
public interface  UserRepository extends JpaRepository<User, Long>{
    Optional<User> findByUsername(final String username);
}
