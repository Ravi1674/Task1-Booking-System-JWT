package com.bookingsystem.api.entities;

import lombok.Data;

@Data
public class JwtRequest {
	private String userName;
	private String userEmail;
	private String password;
	
}