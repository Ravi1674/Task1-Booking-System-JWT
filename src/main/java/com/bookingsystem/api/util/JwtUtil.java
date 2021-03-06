package com.bookingsystem.api.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {
//	Secret Key must be confidential and it should not change..
	private static final String JWT_SECRET = "crxtrx";
	private static final int TOKEN_VALIDITY = 3600*4;

	public String getUserNameFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}


//	Higher Order function, which takes fun as argument(part of programming)
	private <T> T getClaimFromToken(String token, Function<Claims, T> claimResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimResolver.apply(claims);
	}

	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token).getBody();
	}

//	validate the token by checking username from the token and from the database as well as it checks token is expired or not.
	public boolean validateToken(String token, UserDetails userDetails) {
		String userName = getUserNameFromToken(token);
		return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

	
	private boolean isTokenExpired(String token) {
		
		final Date expirationDate = getExpirationdateFromToken(token);
		System.out.println("Date : "+expirationDate);
		return expirationDate.before(new Date());
	}

	private Date getExpirationdateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}
	
//	Generate the token and set the expire time of the token..
	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();

		return Jwts.builder().setClaims(claims).setSubject(userDetails.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + TOKEN_VALIDITY*1000))
				.signWith(SignatureAlgorithm.HS512, JWT_SECRET).compact();
	}
}
