package com.example.amazinggamesbackend.security;

import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JWTFilter extends OncePerRequestFilter {


    @Autowired private MyUserDetailsService userDetailsService;
    @Autowired private JWTUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        // Extracting the "Authorization" header
        String authHeader = request.getHeader("Authorization");

        // Checking if the header contains a Bearer token
        if(authHeader != null && !authHeader.isBlank() && authHeader.startsWith("Bearer ")){
            // Extract JWT
            String jwt = authHeader.substring(7);
            if(jwt == null || jwt.isBlank()){
                // Invalid JWT
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid JWT Token in Bearer Header");
            }else {
                try{
                    // Verify token and extract email
                    String username = jwtUtil.validateTokenAndRetrieveSubject(jwt);

                    // Fetch User Details
                    System.out.println(username + "FILTER LOG");
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                    // Create Authentication Token
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(username, userDetails.getPassword(), userDetails.getAuthorities());

                    // Setting the authentication on the Security Context using the created token
                    if(SecurityContextHolder.getContext().getAuthentication() == null){
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }
                }catch(JWTVerificationException exc){
                    // Failed to verify JWT
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid JWT Token");
                }
            }
        }

        // Continuing the execution of the filter chain
        filterChain.doFilter(request, response);
    }
}