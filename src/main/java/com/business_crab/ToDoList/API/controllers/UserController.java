package com.business_crab.ToDoList.API.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
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
}
