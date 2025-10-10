package com.business_crab.ToDoList.API.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.business_crab.ToDoList.API.entities.User;
import com.business_crab.ToDoList.API.repositories.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }
}
