package com.business_crab.ToDoList.API.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

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

    public User create(final User user) {
        Optional<User> optionalUser = userRepository.findByEmail(user.getEmail());
        if (optionalUser.isPresent()) {
            throw new IllegalStateException("User with this email already exists");
        }
        return userRepository.save(user);
    }

    public User update(final @RequestBody User newUser , final @PathVariable Long id) {
        return userRepository.findById(id).map(user -> {
            user.setName(newUser.getName());
            user.setEmail(newUser.getEmail());
            user.setPassword(newUser.getPassword());
            return userRepository.save(user);
        }).orElseGet(() -> {
            return userRepository.save(newUser);
        });
    }

    public void delete(final Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            throw new IllegalStateException("The with such id is not found");
        }
        userRepository.deleteById(id);
    }
}