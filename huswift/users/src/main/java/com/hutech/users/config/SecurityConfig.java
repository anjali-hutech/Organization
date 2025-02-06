package com.hutech.users.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Disable CSRF (enable in production for better security)
            .authorizeHttpRequests(auth -> auth
                // Public APIs (no token required)
                .requestMatchers("/api/user/signup", "/api/user/signin", "/api/organizations").permitAll()

                // Role-based APIs
                .requestMatchers("/api/organizations", "/api/organizations/**").permitAll()

                .requestMatchers("/api/admin/**").hasRole("ADMIN")
                .requestMatchers("/api/fleet-manager/**").hasRole("FLEET_MANAGER")
                .requestMatchers("/api/driver/**").hasRole("DRIVER")
                .requestMatchers("/api/dispatcher/**").hasRole("DISPATCHER")
                .requestMatchers("/api/maintenance/**").hasRole("MAINTENANCE_TECHNICIAN")
                .requestMatchers("/api/finance/**").hasRole("FINANCE")
                .requestMatchers("/api/safety/**").hasRole("SAFETY_OFFICER")
                .requestMatchers("/api/customer/**").hasRole("CUSTOMER")

                // All other requests need authentication
                .anyRequest().authenticated()
            )
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
    
   
}
