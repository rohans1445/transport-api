package com.example.transportapi.security;

import com.example.transportapi.service.UserService;
import com.example.transportapi.util.JwtUtil;
import lombok.extern.java.Log;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Log
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserService userService;

    public JwtAuthenticationFilter(JwtUtil jwtUtil, UserService userService) {
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info(":::::::: inside JwtAuthenticationFilter");
        String token = getTokenFromRequest(request);

        try {
            if(token != null && jwtUtil.validateToken(token)){
                log.info(":::::::: Token validated successfully for user - " + jwtUtil.getUsernameFromToken(token));
                CustomUserDetails userDetails = (CustomUserDetails) userService.loadUserByUsername(jwtUtil.getUsernameFromToken(token));
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword());
                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        } catch (Exception e){
            log.info(":::::::: JWT Filter error ");
            throw new RuntimeException("Something went wrong while authenticating with JWT.", e.getCause());
        }

    }

    private String getTokenFromRequest(HttpServletRequest request) {

        String token = request.getHeader("Authorization");
        if(token != null){
            return token.substring(7);
        }

        return null;
    }
}
