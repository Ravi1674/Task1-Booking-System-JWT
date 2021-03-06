package com.bookingsystem.api.controller;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bookingsystem.api.entities.User;
import com.bookingsystem.api.service.UserService;

@RestController
public class UserController {

	@Autowired
	public UserService userService;
	
//	Set the Roles in the database and Admin Roles
	@PostConstruct
	public void initRolesAndUsers() {
		userService.initRolesAndUser();
	}

	@PostMapping({ "/registerUser" })
	public User registerNewUser(@RequestBody User user) {
		return userService.registerNewUser(user);
	}

	/* For Testing purpose of access the URL based on Role...
	 * @GetMapping({ "/forAdmin" })
	 * 
	 * @PreAuthorize("hasRole('Admin')") public String forAdmin() { return
	 * "Admin only has access to this URL"; }
	 * 
	 * @GetMapping({ "/forUser" })
	 * 
	 * @PreAuthorize("hasRole('User')") public String forUser() { return
	 * "User only has access to this URL"; }
	 */
	
	
	
}
