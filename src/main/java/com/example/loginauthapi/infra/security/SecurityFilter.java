package com.example.loginauthapi.infra.security;

import com.example.loginauthapi.domain.user.User;
import com.example.loginauthapi.repositories.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Component
public class SecurityFilter extends OncePerRequestFilter {
    @Autowired
    TokenService tokenService;

    @Autowired
    UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var token = this.recoverToken(request); // Recupera o token do cabeçalho Authorization
        var login = tokenService.validateToken(token); // Valida o token e retorna o email ou login do usuário

        if (login != null) {
            // Busca o usuário pelo email retornado do token JWT
            User user = userRepository.findByEmail(login).orElseThrow(() -> new RuntimeException("User Not Found"));

            // Aqui você pode definir as permissões (roles) com base no que está armazenado no token ou no banco de dados
            List<SimpleGrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(user.getRole().name())); // Usa a role do usuário real

            // Cria o objeto de autenticação com o usuário e suas permissões
            var authentication = new UsernamePasswordAuthenticationToken(user, null, authorities);

            // Define o contexto de segurança para este request
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        // Continua o filtro para o próximo na cadeia
        filterChain.doFilter(request, response);
    }

    // Recupera o token JWT do cabeçalho Authorization
    private String recoverToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return null; // Retorna null se o cabeçalho não contiver o token JWT
        }
        return authHeader.replace("Bearer ", ""); // Remove a parte "Bearer " do token
    }
}
