package com.business_crab.ToDoList.API.config.security;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.business_crab.ToDoList.API.model.entity.User;
import com.business_crab.ToDoList.API.repository.UserRepository;

@Configuration
public class UserDetailsServiceConfig {
    
    @Bean
    public UserDetailsService userDetailsService(final UserRepository userRepository) {
        return username -> {
            Optional<User> user = userRepository.findByUsername(username);
            if (!user.isPresent()) {
                throw new UsernameNotFoundException("User is not found");
            }
            return new org.springframework.security.core.userdetails.User(user.get().getUsername() ,
                                                                          user.get().getPassword() ,
                                                                          new ArrayList<>());
        };
    }
}
