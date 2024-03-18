package com.example.wouaffy.service;

import java.util.Optional;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.wouaffy.Repository.UserRepository;
import com.example.wouaffy.entity.User;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	public void signUp(User user) {
		
		Optional<User> userFound = this.userRepository.findByEmail(user.getEmail());
		
		if(userFound.isPresent()) {
			throw new RuntimeErrorException(null, "E-mail already exist");
		}
		
		String encryptPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encryptPassword);
		this.userRepository.save(user);
	}
	
}
