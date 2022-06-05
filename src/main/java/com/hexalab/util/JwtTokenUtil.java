package com.hexalab.util;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.hexalab.entity.UserEntity;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtTokenUtil {

	private static final long EXPIRE_DURATION = 3600000;

	@Value("${app.jwt.secret}")
	private String secretKey;

	public String generateAccessToken(UserEntity user) {
		return Jwts.builder().setSubject(String.format("%s,%s", user.getId(), user.getEmail())).setIssuer("CodeJava")
				.setIssuedAt(new Date()).setExpiration(new Date(System.currentTimeMillis() + EXPIRE_DURATION))
				.signWith(SignatureAlgorithm.HS512, secretKey).compact();
	}

	public boolean validateAccessToken(String token) {
		try {
			Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
			return true;
		} catch (ExpiredJwtException | IllegalArgumentException | MalformedJwtException | UnsupportedJwtException
				| SignatureException ex) {
			ex.printStackTrace();
		}

		return false;
	}

	public String getSubject(String token) {
		return parseClaims(token).getSubject();
	}

	private Claims parseClaims(String token) {
		return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
	}

}
