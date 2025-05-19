package br.app.mentesaudavel.api.modules.security.infrastructure.filters;

import br.app.mentesaudavel.api.modules.authentication.infrastructure.services.UserDetailsServiceImpl;
import br.app.mentesaudavel.api.modules.security.services.JwtService;
import br.app.mentesaudavel.api.shared.exceptions.BadRequestException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static br.app.mentesaudavel.api.modules.security.infrastructure.configuration.WebSecurityConfiguration.PUBLIC_POST;

@Component
@RequiredArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String path = request.getRequestURI();

        if (List.of(PUBLIC_POST).contains(path)) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = this.recoverToken(request);

        String userId = this.jwtService.validate(token);
        UserDetails userDetails = this.userDetailsService.loadUserBydId(UUID.fromString(userId));

        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null) {
            throw new BadRequestException("Access token is missing", Map.of("access_token", new String[] {"Access token is required."}));
        }

        if (!authHeader.startsWith("Bearer ")) {
            throw new BadRequestException("Invalid access token provided.", Map.of("token", new String[] {"Token is malformed"}));
        }

        return authHeader.replace("Bearer ", "");
    }
}
