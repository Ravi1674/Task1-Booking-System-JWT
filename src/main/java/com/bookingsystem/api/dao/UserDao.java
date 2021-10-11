package com.bookingsystem.api.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.bookingsystem.api.entities.User;

public interface UserDao extends CrudRepository<User, String>  {

//	Optional<User> findByUserNameOrUserEmail(String userName);

	@Query()
	public User findByUserNameOrUserEmail(String userName, String userEmail);
	
	@Query()
	public User findByUserName(String userName);
	
	@Query()
	public User findByUserEmail(String userEmail);
	
}
