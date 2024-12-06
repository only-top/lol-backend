package com.lol_backend.lol_backend.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.lol_backend.lol_backend.services.UserService;

import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig   {
	private final JwtFilter jwtFilter;
	
	public SecurityConfig(JwtFilter jwtFilter) {
		this.jwtFilter = jwtFilter;
	}
	
	
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	    
	    http.csrf(csrf -> csrf.disable());    // Disabilitare CSRF
	    http.authorizeHttpRequests(auth -> {				// Configurare le richieste autorizzate
	        auth.requestMatchers("/api/auth/**").permitAll();
	        auth.anyRequest().authenticated();
	    });
	    http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));	// Configurazione della gestione della sessione
	    http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class); 		// Aggiunta del filtro JWT

	    return http.build();
	}
}
