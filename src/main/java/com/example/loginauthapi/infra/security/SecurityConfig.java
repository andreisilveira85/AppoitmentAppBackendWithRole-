package com.example.loginauthapi.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.POST, "/auth/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/appointments").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/appointments/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/appointments/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/appointments").hasAnyRole("DOCTOR", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/appointments").hasAnyRole("DOCTOR", "ADMIN", "PATIENT")
                        .requestMatchers(HttpMethod.PUT, "/appointments/**").hasRole("DOCTOR")
                        .requestMatchers(HttpMethod.POST, "/appointments").hasAnyRole("RECEPTIONIST", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/appointments").hasAnyRole("RECEPTIONIST", "ADMIN", "DOCTOR")
                        .requestMatchers(HttpMethod.GET, "/appointments/patient/**").hasRole("PATIENT")
                        .anyRequest().denyAll()
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
