package com.lol_backend.lol_backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.lol_backend.lol_backend.entities.User;
import com.lol_backend.lol_backend.repositories.UserRepository;

@Service
public class UserService {
	
	@Autowired
	UserRepository userRepository;
	
	private BCryptPasswordEncoder passwordEncoder= new BCryptPasswordEncoder();
	
	public User registerUser(String username, String email, String password) {
		User user = new User();
		user.setUsername(username);
		user.setEmail(email);
		user.setPasswordHash(passwordEncoder.encode(password));
		return userRepository.save(user);
	}
	
	boolean isPasswordValid(String password, String encodePassword) {
		return passwordEncoder.matches(encodePassword, password);
	}
}
