package com.lol_backend.lol_backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lol_backend.lol_backend.dto.LoginRequest;
import com.lol_backend.lol_backend.dto.LoginResponse;
import com.lol_backend.lol_backend.entities.User;
import com.lol_backend.lol_backend.repositories.UserRepository;
import com.lol_backend.lol_backend.services.JwtService;
import com.lol_backend.lol_backend.services.UserService;

@RestController
@RequestMapping ("api/auth")
public class AuthController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private JwtService jwtService;
	
	@PostMapping("/register")
	public User register(@RequestBody User user) {
		return userService.registerUser(user.getUsername(), user.getEmail(), user.getPasswordHash());
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
		User user = userRepository.findByUsername(loginRequest.getUsername())
				.orElseThrow(() -> new RuntimeException("Utente non trovato"));
		
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPasswordHash())) {
			return ResponseEntity.badRequest().body("Credenziali non valide");
		}
		
		String token = jwtService.generateToken(user.getUsername());
		return ResponseEntity.ok(new LoginResponse(token));
	}
	
}
