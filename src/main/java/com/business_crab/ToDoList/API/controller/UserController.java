package com.business_crab.ToDoList.API.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.business_crab.ToDoList.API.config.security.JwtUtil;
import com.business_crab.ToDoList.API.model.dto.DataResponse;
import com.business_crab.ToDoList.API.model.dto.TokenResponse;
import com.business_crab.ToDoList.API.model.entity.User;
import com.business_crab.ToDoList.API.service.UserService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/auth")
@Tag(name="User" , description="API for User Operations")
public class UserController {
    
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    public UserController(final UserService userService ,
                          final AuthenticationManager authenticationManager ,
                          final JwtUtil jwtUtil ,
                          final UserDetailsService  userDetailsService)
    {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/register")
    public ResponseEntity<DataResponse<User>> save(@RequestBody User user) {
        DataResponse<User> response = new DataResponse<>();
        if (Objects.isNull(user)) {
            response.setMessage("Payload cannot be Null");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        if (userService.findByUsername(user.getUsername())) {
            response.setMessage("Usetname is already taken");
            return ResponseEntity.ok(response);
        }
        response.setData(userService.save(user));
        response.setMessage(HttpStatus.CREATED.toString());
        return ResponseEntity.ok(response);
     }

     @PostMapping("/login")
     public ResponseEntity<DataResponse<List<TokenResponse>>> createAuthenticationToken(final @RequestBody User user)
     throws BadCredentialsException {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername() , user.getPassword()));

        final UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails);

        List<TokenResponse> tokens = new ArrayList<>();
        tokens.add(new TokenResponse(jwt));

        DataResponse<List<TokenResponse>> response = new DataResponse<>();
        response.setData(tokens);
        response.setMessage(HttpStatus.OK.toString());

        return ResponseEntity.ok(response);
     }

     @PostMapping("/refresh-token")
     public ResponseEntity<DataResponse<List<TokenResponse>>> refreshToken(final @RequestBody TokenResponse token) {
        final DataResponse<List<TokenResponse>> response = new DataResponse<>();
        final List<TokenResponse> tokens = new ArrayList<>();

        if (token == null) {
            response.setMessage("Token is missing or invalid");
            return ResponseEntity.badRequest().body(response);
        }

        final String username = jwtUtil.extractUsernameFromToken(token.getToken());
        if (!this.userService.findByUsername(username)) {
            response.setMessage("User is not found");
            return ResponseEntity.status(403).body(response);
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        final String newAccessToken = jwtUtil.generateToken(userDetails);

        tokens.add(new TokenResponse(newAccessToken));

        response.setData(tokens);
        response.setMessage(HttpStatus.CREATED.toString());

        return ResponseEntity.ok(response);
     }
}
