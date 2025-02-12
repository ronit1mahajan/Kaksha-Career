package com.kc1.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.AllArgsConstructor;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {

    private final CustomJwtAuthenticationFilter jwtFilter;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-resources/**", "/webjars/**").permitAll()
                .requestMatchers(HttpMethod.OPTIONS).permitAll()
                .requestMatchers("/users/login", "/users/register","users/logout").permitAll()
                .requestMatchers("/colleges/all", "/colleges/{id}", "/colleges/search","/colleges/type/{type}").permitAll()
                .requestMatchers("/courses/get/{courseId}", "/courses/all","/courses/search", "/courses/searchByName").permitAll()
                
                .requestMatchers( "/reviews/get/{reviewId}", "/reviews/college/{collegeId}", "/reviews/user/{userId}", "/reviews/college/name/{collegeName}", "/reviews/college/{collegeId}/average-rating", "/reviews/rating/{rating}").permitAll()
                .requestMatchers("/applications/user/{userId}").permitAll()

                // Admin-only access
                .requestMatchers("/courses/add", "/courses/update/{courseId}","/courses/delete/{courseId}","/courses/aCintoC").hasRole("ADMIN")
                .requestMatchers("/colleges/add", "/colleges/update/{collegeId}", "/colleges/delete/{id}").hasRole("ADMIN")
                .requestMatchers("/applications/college/{collegeId}", "/applications/course/{courseId}","/applications/{applicationId}/applicationStatus","/applications/pending").hasRole("ADMIN")
                .requestMatchers("/notifications/all", "/notifications/unread").hasRole("ADMIN")

                // Student-only access
                .requestMatchers("/users/delete/{userId}").hasRole("STUDENT")
                .requestMatchers("/reviews/add","/reviews/update/{reviewId}","/reviews/delete/{reviewId}").hasRole("STUDENT")
                .requestMatchers("/applications/submit","/applications/apply","applications/accepted/{userId}").hasRole("STUDENT")
                .requestMatchers( "/reviews/update/**", "/reviews/delete/**").hasRole("STUDENT")

                // User-specific access
                .requestMatchers("/users/{userId}").hasAnyRole("ADMIN", "STUDENT")

                // Any other request should be authenticated
                .anyRequest().authenticated()
            )
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
