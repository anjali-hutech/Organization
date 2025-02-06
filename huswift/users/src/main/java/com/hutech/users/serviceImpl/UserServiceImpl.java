package com.hutech.users.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hutech.users.dto.CreateUserRole;
import com.hutech.users.dto.Responses;
import com.hutech.users.entity.User;
import com.hutech.users.repository.UserRepository;
import com.hutech.users.security.JwtUtil;
import com.hutech.users.service.UserService;



@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepository;


	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private JavaMailSender mailSender;

	@Value("${spring.mail.username}")
	private String senderEmail;

	@Override
	public CreateUserRole signup(User user) {
		if (userRepository.findByEmailIgnoreCase(user.getEmail()).isPresent()) {
			throw new RuntimeException("Email is already in use.");
		}
		if (!user.getPassword().equals(user.getConfirmPassword())) {
			throw new RuntimeException("Passwords do not match.");
		}

		user.setPassword(passwordEncoder.encode(user.getPassword()));

		User savedUser = userRepository.save(user);
		return new CreateUserRole(new Responses("success", "User registered successfully!", savedUser.getId()));
	}

}
