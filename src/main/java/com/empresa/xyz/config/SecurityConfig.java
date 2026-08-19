package com.empresa.xyz.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder encoder) {
        var admin = User.withUsername("admin")
                .password(encoder.encode("admin123"))
                .roles("ADMIN")
                .build();

        var supervisor = User.withUsername("supervisor")
                .password(encoder.encode("super123"))
                .roles("SUPERVISOR")
                .build();

        return new InMemoryUserDetailsManager(admin, supervisor);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(HttpMethod.POST, "/api/camiones").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/conductores").hasRole("ADMIN")

                .requestMatchers(HttpMethod.PUT, "/api/camiones/*/asociar-conductor/*")
                        .hasAnyRole("ADMIN", "SUPERVISOR")

                .requestMatchers(HttpMethod.GET, "/api/**").hasAnyRole("ADMIN", "SUPERVISOR")

                .requestMatchers("/h2-console/**").hasRole("ADMIN")

                .anyRequest().authenticated()
            )
            .httpBasic(Customizer.withDefaults())
            .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin())); 

        return http.build();
    }
}
