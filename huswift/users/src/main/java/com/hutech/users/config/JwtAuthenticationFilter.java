package com.hutech.users.config;

import java.io.IOException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.hutech.users.security.JwtUtil;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtUtil jwtUtil;

	public JwtAuthenticationFilter(JwtUtil jwtUtil) {
		this.jwtUtil = jwtUtil;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
	        throws ServletException, IOException {
	    String uri = request.getRequestURI();

	    if (uri.startsWith("/api/user/signup") || uri.startsWith("/api/user/signin") || uri.startsWith("/api/organizations")) {
	        filterChain.doFilter(request, response);
	        return;
	    }

	    String authHeader = request.getHeader("Authorization");

	    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
	        sendErrorResponse(response, "Missing or invalid Authorization header");
	        return;
	    }

	    String token = authHeader.substring(7);

	    try {
	        Claims claims = jwtUtil.validateToken(token);
	        String email = claims.getSubject();
	        String userId = claims.get("userId", String.class);

	        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userId, null, null);
	        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

	        SecurityContextHolder.getContext().setAuthentication(authToken);
	    } catch (RuntimeException e) {
	        sendErrorResponse(response, e.getMessage());
	        return;
	    }

	    filterChain.doFilter(request, response);
	}

	private void sendErrorResponse(HttpServletResponse response, String errorMessage) throws IOException {
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.setContentType("application/json");
		response.getWriter().write("{\"error\": \"" + errorMessage + "\"}");
	}
}