package com.bookingsystem.api.configuration;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.bookingsystem.api.service.JwtService;
import com.bookingsystem.api.util.JwtUtil;

import io.jsonwebtoken.ExpiredJwtException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private JwtService jwtService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
//		Get the Bearer token from Authorization....
		final String header = request.getHeader("Authorization");

		String jwtToken = null;
		String userName = null;
		if (header != null && header.startsWith("Bearer ")) {
//			Store the token to the jwtToken
			jwtToken = header.substring(7);
			
//			Fetch the username from jwt token and if token is expired it will generate exception
			try {
				userName = jwtUtil.getUserNameFromToken(jwtToken);
				System.out.println("Token user name :"+userName);
			} catch (IllegalArgumentException exception) {
				System.out.println("Unable To get Token");
			} catch (ExpiredJwtException exception) {
				System.out.println("Token is expired");
			}
		} else {
			System.out.println("Bearer is not prefix of this Token...");
		}
		
		if(userName!=null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails =jwtService.loadUserByUsername(userName);
			
			if(jwtUtil.validateToken(jwtToken, userDetails)) {
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				
				usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
		}
		
		filterChain.doFilter(request, response);
	}
}