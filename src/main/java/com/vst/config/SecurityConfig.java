package com.vst.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // 1. ADD THIS LINE to enable CORS using your new bean
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            
            // 2. Make sure to disable CSRF for stateless APIs
            .csrf(csrf -> csrf.disable()) 
            
            .authorizeHttpRequests(authz -> authz
                // Make sure your /api/auth/** routes are permitted
                .requestMatchers("/api/auth/**").permitAll() 
                .anyRequest().authenticated()
            );
            
        // ... add any other configurations you have (like .httpBasic(), .sessionManagement())

        return http.build();
    }

    // 3. ADD THIS BEAN to define your CORS rules
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        
        // This is your React app's URL
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:5174")); 
        
        // Allow common methods
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS")); 
        
        // Allow all headers
        configuration.setAllowedHeaders(Arrays.asList("*")); 
        
        // Allow credentials (like cookies) if you use them
        configuration.setAllowCredentials(true); 
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // Apply this to all routes
        return source;
    }
    
    // ... any other beans you have (like PasswordEncoder)
}