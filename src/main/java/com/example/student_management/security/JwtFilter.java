package com.example.student_management.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtFilter extends UsernamePasswordAuthenticationFilter {

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            Claims claims = Jwts.parser()
                                .setSigningKey("your_secret_key")
                                .parseClaimsJws(token)
                                .getBody();
            String username = claims.getSubject();
            UserDetails userDetails = myUserDetailsService.loadUserByUsername(username);
            if (userDetails != null) {
                SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()));
            }
        }
        chain.doFilter(request, response);
    }
}
