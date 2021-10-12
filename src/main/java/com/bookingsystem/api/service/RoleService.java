package com.bookingsystem.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookingsystem.api.dao.RoleDao;
import com.bookingsystem.api.entities.Role;

@Service
public class RoleService {
	
	@Autowired
	private RoleDao roleDao;
	
//	create the new Role
	public Role createNewRole(Role role) {
		return roleDao.save(role);
	}
}