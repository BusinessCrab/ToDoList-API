package com.business_crab.ToDoList.API.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.business_crab.ToDoList.API.entities.User;
import com.business_crab.ToDoList.API.services.UserService;

@RestController
@RequestMapping(path = "api/users")
public class UserController {
    private final UserService userService;

    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> findAll() {
        return userService.findAll();
    }

    @PostMapping
    public User create(final @RequestBody User user) {
        
        return userService.create(user);
    }

    @PutMapping(path = "{id}")
    public User update(final @RequestBody User newUser , final @PathVariable Long id) {
        return userService.update(newUser, id);
    }

    @DeleteMapping(path = "{id}")
    public void delete(final @PathVariable(name = "id") Long id) {
        userService.delete(id);
    }
}
