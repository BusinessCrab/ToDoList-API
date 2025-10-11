package com.business_crab.ToDoList.API.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.business_crab.ToDoList.API.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    /*@Query(value = "select * from users where email = :email")
    public User findByEmail(final @Param(value = "email") String email);*/

    public Optional<User> findByEmail(final String email);
}