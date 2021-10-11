package com.bookingsystem.api.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.bookingsystem.api.entities.Role;

@Repository
public interface RoleDao extends CrudRepository<Role, String> {
	
}