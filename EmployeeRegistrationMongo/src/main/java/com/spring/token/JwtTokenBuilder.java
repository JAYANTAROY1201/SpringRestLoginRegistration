package com.spring.token;

import io.jsonwebtoken.*;
import java.util.Date;
import javax.xml.bind.DatatypeConverter;

import com.spring.model.EmployeeBean;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;

/**
 * purpose: this class is designed to generate a JWT token and parse it
 * @author JAYANTA ROY
 * @version 1.0
 * @since 10/07/18
 */
public class JwtTokenBuilder {
	final static String KEY = "JAYANTA";

	/**
	 * Method to create token
	 * @param emp
	 * @return
	 */
	public String createJWT(EmployeeBean emp) {
		// The JWT signature algorithm we will be using to sign the token
		String id = emp.getId();
		String subject = emp.getEmail();
		String issuer = emp.getUserName();
		Date now = new Date();
		// Let's set the JWT Claims
		JwtBuilder builder = Jwts.builder().setId(id).setIssuedAt(now).setSubject(subject).setIssuer(issuer)
				.signWith(SignatureAlgorithm.HS256, KEY);

		// Builds the JWT and serializes it to a compact, URL-safe string
		return builder.compact();
	}

	/**
	 * Method to access token
	 * @param jwt
	 */
	public void parseJWT(String jwt) {

		// This line will throw an exception if it is not a signed JWS (as expected)
		Claims claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(KEY)).parseClaimsJws(jwt)
				.getBody();
		System.out.println("ID: " + claims.getId());
		System.out.println("Subject: " + claims.getSubject());
		System.out.println("Issuer: " + claims.getIssuer());
		System.out.println("Expiration: " + claims.getExpiration());
	}
}
