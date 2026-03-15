package com.fintaxpro.user.config;

import com.fintaxpro.user.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private static final Logger log = LoggerFactory.getLogger(SecurityConfig.class);

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        log.debug("Initializing BCryptPasswordEncoder");
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        log.info("Configuring Spring Security filter chain");

        http
                .csrf(csrf -> {
                    log.debug("Disabling CSRF protection");
                    csrf.disable();
                })
                .authorizeHttpRequests(auth -> {
                    log.debug("Configuring public and protected endpoints");

                    // Public endpoints
                    auth.requestMatchers(
                            "/api/v1/users/register",
                            "/api/v1/users/login",
                            "/v3/api-docs/**",
                            "/swagger-ui/**",
                            "/swagger-ui.html"
                    ).permitAll();

                    // ADMIN-only endpoint
                    auth.requestMatchers("/api/v1/users/all").hasAuthority("ADMIN");

                    // USER + ADMIN endpoints (tax)
                    auth.requestMatchers("/api/v1/tax/**").hasAnyAuthority("USER", "ADMIN");

                    // Everything else requires authentication
                    auth.anyRequest().authenticated();
                })
                .sessionManagement(session -> {
                    log.debug("Setting session policy to STATELESS");
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                });

        log.debug("Adding JwtAuthenticationFilter before UsernamePasswordAuthenticationFilter");
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        log.info("Security filter chain configured successfully");
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        log.debug("Initializing AuthenticationManager");
        return config.getAuthenticationManager();
    }
}
