package com.example.agenda.config;

import java.io.IOException;
import java.util.Collections;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.agenda.domain.Usuario.Usuario;
import com.example.agenda.repository.UsuarioRepository;
import com.example.agenda.service.TokenService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityFilter extends OncePerRequestFilter {
    private final TokenService tokenService;
    private final UsuarioRepository usuarioRepository;

    public SecurityFilter(TokenService tokenService, UsuarioRepository usuarioRepository) {
        this.tokenService = tokenService;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws IOException, jakarta.servlet.ServletException {

        var token = this.recoverToken(request);

        if (token != null) {
            var subjectId = tokenService.ValidateToken(token);

            if(!subjectId.isEmpty()) {
                Integer id = Integer.valueOf(subjectId);
                Usuario usuario = usuarioRepository.findById(id).orElse(null);
                
                if(usuario != null) {
                    var authentication = new UsernamePasswordAuthenticationToken(usuario, null, Collections.emptyList());
                    SecurityContextHolder.getContext().setAuthentication(authentication);   
                }
            }
        }

        filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");

        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
            return null;
        }

        return authHeader.replace("Bearer ", "");
    }
}
