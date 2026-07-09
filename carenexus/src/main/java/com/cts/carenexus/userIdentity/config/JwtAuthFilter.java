package com.cts.carenexus.userIdentity.config;

import com.cts.carenexus.userIdentity.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String requestURI = request.getRequestURI();

        if (requestURI.contains("/api/v1/auth/register") || requestURI.contains("/api/v1/auth/login")) {
            System.out.println("JwtAuthFilter: Bypassing auth check for URI: " + requestURI);
            filterChain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            try {
                username = jwtService.extractUsername(token);
                System.out.println("JwtAuthFilter: Extracted Username/Email: " + username);
            } catch (Exception e) {
                System.err.println("JwtAuthFilter ERROR: Token extraction failed! Reason: " + e.getMessage());
            }
        } else {
            System.out.println("JwtAuthFilter: No Bearer Token found in Authorization header for URI: " + requestURI);
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            if (jwtService.validateToken(token, userDetails.getUsername())) {
                System.out.println("JwtAuthFilter: Token validated successfully for user: " + username);

                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authToken);
            } else {
                System.err.println("JwtAuthFilter WARNING: Token validation failed for user: " + username);
            }
        }
        filterChain.doFilter(request, response);
    }
}