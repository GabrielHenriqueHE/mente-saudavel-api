package com.mentesaudavel.mentesaudavel.infrastructure.security.filter;

import com.mentesaudavel.mentesaudavel.core.exceptions.BadRequestException;
import com.mentesaudavel.mentesaudavel.core.security.implementations.UserDetailsServiceImpl;
import com.mentesaudavel.mentesaudavel.core.security.services.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

import static com.mentesaudavel.mentesaudavel.infrastructure.security.constants.Endpoints.PUBLIC_ENDPOINTS;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String path = request.getRequestURI();

        if (PUBLIC_ENDPOINTS.contains(path)) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = this.recoverToken(request);

        String userId = this.tokenService.validateAccessToken(token);
        UserDetails user = this.userDetailsService.loadUserById(UUID.fromString(userId));

        var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");

        if (authHeader == null) {
            throw new BadRequestException("Access token is missing");
        }

        return authHeader.replace("Bearer ", "");
    }
}
