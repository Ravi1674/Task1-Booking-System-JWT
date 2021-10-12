package com.bookingsystem.api.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bookingsystem.api.dao.UserDao;
import com.bookingsystem.api.entities.JwtRequest;
import com.bookingsystem.api.entities.JwtResponse;
import com.bookingsystem.api.entities.User;
import com.bookingsystem.api.util.JwtUtil;

@Service
public class JwtService implements UserDetailsService {

	@Autowired
	private UserDao userDao;

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private AuthenticationManager authenticationManager;

//	creating jwt token
	public JwtResponse createJwtToken(JwtRequest jwtRequest) throws Exception {
		String userName = jwtRequest.getUserName();
		String userEmail = jwtRequest.getUserEmail();
		String userPw = jwtRequest.getPassword();
		
		String userCredential = userName!=null?userName:userEmail;
		System.out.println("EmailName or UseName : "+userCredential);
		authenticate(userCredential, userPw);
		
		final UserDetails userDetails = loadUserByUsername(userCredential);
		String newGeneratedToken = jwtUtil.generateToken(userDetails);
		User user = userDao.findByUserNameOrUserEmail(userName, userEmail);
		System.out.println(user);
		return new JwtResponse(user, newGeneratedToken);
	}

	
	/*
	 * public UserDetails loadUserByUsername(String userName) { User user = null;
	 * System.out.println("user : "+user); return new
	 * org.springframework.security.core.userdetails.User(user.getUserName(),
	 * user.getUserPassword(), getAuthorities(user)); }
	 */
	@Override
	public UserDetails loadUserByUsername(String userCredential) throws UsernameNotFoundException {
		User user;
		
//		Fire the query according to whether useremail is entered or username...
		
		if(userCredential.contains("@")) {
			user = userDao.findByUserEmail(userCredential);
		}
		else {
			user = userDao.findByUserName(userCredential);
		}
//		System.out.println(userName+" iii "+userEmail);
//		User user = userDao.findByUserNameOrUserEmail(userName, userEmail);
		System.out.println("user : "+user);
		if (user != null) {
			return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getUserPassword(),
					getAuthorities(user));
		} else {
			throw new UsernameNotFoundException("Username or email is not valid");
		}
	}

//	Method for Providing the Role to the user and based on that api will give access...
	private Set<SimpleGrantedAuthority> getAuthorities(User user) {
		Set<SimpleGrantedAuthority> authorities = new HashSet<SimpleGrantedAuthority>();

		user.getRole().forEach(role -> {
			authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleName()));
		});

		return authorities;
	}
	
//	Authentication checks(credentials) entered by user and handled exception..

	private void authenticate(String userCredential, String userPw) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userCredential, userPw));
		} catch (DisabledException exception) {
			throw new Exception("User is disabled");
		} catch (BadCredentialsException exception) {
			throw new Exception("Bad Credential from user");
		}
	}
}
