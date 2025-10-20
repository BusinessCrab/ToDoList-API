package com.business_crab.ToDoList.API.config.security;

import java.io.IOException;
import java.util.Objects;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    public JwtAuthorizationFilter(final AuthenticationManager authenticationManager ,
                                  final JwtUtil jwtUtil ,
                                  final UserDetailsService userDetailsService)
    {
        super(authenticationManager);
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    private UsernamePasswordAuthenticationToken getAuthentication(final HttpServletRequest request) {
        final String token = request.getHeader("Authorization");
        UsernamePasswordAuthenticationToken authentication = null;
        if (Objects.nonNull(token)) {
            final String username  = jwtUtil.extractUsernameFromToken(token.replace("Bearer ", ""));
            if (Objects.nonNull(username)) {
                final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                if (jwtUtil.validateToken(token.replace("Bearer ", ""), userDetails)) {
                    authentication = new UsernamePasswordAuthenticationToken(userDetails, null , userDetails.getAuthorities());
                }
            }
        }

        return authentication;
    }

    @Override
    protected void doFilterInternal(final HttpServletRequest request ,
                                    final HttpServletResponse response ,
                                    final FilterChain chain)
    throws IOException, ServletException {
        final String header = request.getHeader("Authorization");
        if (Objects.isNull(header) || !header.startsWith("Bearer ")) {
            chain.doFilter(request , response);
            return;
        }
        UsernamePasswordAuthenticationToken authenticationToken = getAuthentication(request);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        chain.doFilter(request, response);
    }
}