package com.hutech.users.entity;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users")
public class User {

	@Id
	private String id;

	private String firstName;

	private String lastName;

	private String mobile;

	private String password;

	private String email;

//	@DBRef
	private String role;

	@Transient
	private String confirmPassword;

    private boolean isActive = true;

	private String otp;

	private Date otpExpirationTime;
	
    private List<String> loginHistory;


}