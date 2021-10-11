package com.bookingsystem.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bookingsystem.api.entities.Role;
import com.bookingsystem.api.service.RoleService;

@RestController
public class RoleController {
	
	@Autowired
	public RoleService roleService;
	
	@PostMapping({"/createNewRole"})
	public Role createNewRole(@RequestBody Role role) {
		return roleService.createNewRole(role);
	}
}
