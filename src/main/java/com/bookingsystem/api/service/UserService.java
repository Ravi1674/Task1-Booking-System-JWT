package com.bookingsystem.api.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bookingsystem.api.dao.RoleDao;
import com.bookingsystem.api.dao.UserDao;
import com.bookingsystem.api.entities.Role;
import com.bookingsystem.api.entities.User;

@Service
public class UserService {
	@Autowired
	private UserDao userDao;

	@Autowired
	private RoleDao roleDao;

	@Autowired
	private PasswordEncoder passwordEncoder;

//	Register New user
	public User registerNewUser(User user) {
		Role role = roleDao.findById("User").get();

		Set<Role> roles = new HashSet<>();
		roles.add(role);
		user.setRole(roles);
		user.setUserPassword(getEncodedPassword(user.getUserPassword()));

		return userDao.save(user);
	}

//	Initially set the Roles (Admin and User) in the database and set admin credential hard coded.
	public void initRolesAndUser() {
		Role adminRole = new Role();
		adminRole.setRoleName("Admin");
		adminRole.setRoleDesc("Admin Role");
		roleDao.save(adminRole);

		Role userRole = new Role();
		userRole.setRoleName("User");
		userRole.setRoleDesc("Default Role for new users");
		roleDao.save(userRole);

		User adminUser = new User();
		adminUser.setUserName("admin");
		adminUser.setUserEmail("admin@admin.com");
		adminUser.setUserPassword(getEncodedPassword("admin123"));	
		Set<Role> adminRoles = new HashSet<>();
		adminRoles.add(adminRole);
		adminUser.setRole(adminRoles);
		userDao.save(adminUser);

//		User user = new User();
//		user.setUserName("ravi");
//		user.setUserEmail("ravi@ravi.com");
//		user.setUserPassword(getEncodedPassword("ravi123"));
//		Set<Role> userRoles = new HashSet<>();
//		userRoles.add(userRole);
//		user.setRole(userRoles);
//		userDao.save(user);
	}

//	Encode the password using SHA-1 algorithm
	public String getEncodedPassword(String password) {
		return passwordEncoder.encode(password);
	}

}
