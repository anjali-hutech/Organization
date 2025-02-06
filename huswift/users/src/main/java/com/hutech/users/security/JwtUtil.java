package com.hutech.users.security;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.SignatureException;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtUtil {

	@Value("${jwt.secret}")
	private String secretKey;

	@Value("${jwt.expiration}")
	private long jwtExpiration;

	private Key getSigningKey() {
		byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
		return new SecretKeySpec(keyBytes, SignatureAlgorithm.HS256.getJcaName());
	}

	public String generateToken(String userId, String email) {
		Date now = new Date();
		Date expiryDate = new Date(now.getTime() + jwtExpiration);

		return Jwts.builder().setSubject(email).claim("userId", userId).setIssuedAt(now).setExpiration(expiryDate)
				.signWith(getSigningKey(), SignatureAlgorithm.HS256).compact();
	}

	public String extractSubject(String token) {
		return extractClaims(token).getSubject();
	}

	public Claims validateToken(String token) {
		try {
			return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody();
		} catch (ExpiredJwtException e) {
			throw new RuntimeException("Token expired");
		} catch (UnsupportedJwtException e) {
			throw new RuntimeException("Unsupported JWT token");
		} catch (MalformedJwtException e) {
			throw new RuntimeException("Invalid JWT token");
		} catch (IllegalArgumentException e) {
			throw new RuntimeException("JWT claims string is empty");
		}
	}

	private Claims extractClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody();
	}
}