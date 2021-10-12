package com.bookingsystem.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bookingsystem.api.entities.JwtRequest;
import com.bookingsystem.api.entities.JwtResponse;
import com.bookingsystem.api.service.JwtService;

@RestController
public class JwtController {
	@Autowired
	public JwtService jwtService;
	
//	Authenticate the User and generate the token...
	@PostMapping({"/authenticate"})
	public JwtResponse createJwtToken(@RequestBody JwtRequest jwtRequest) throws Exception {
		return jwtService.createJwtToken(jwtRequest);
	}
}
