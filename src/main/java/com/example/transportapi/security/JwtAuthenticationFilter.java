package com.example.transportapi.security;

import com.example.transportapi.entity.User;
import com.example.transportapi.service.UserService;
import com.example.transportapi.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.debug(":::::::: JwtAuthenticationFilter invoked");
        String token = getTokenFromRequest(request);

        try {
            if(token != null && jwtUtil.validateToken(token)){
                String usernameFromToken = jwtUtil.getUsernameFromToken(token);
                log.debug(":::::::: Token validated successfully for user - " + usernameFromToken);
                log.debug(":::::::: Token issued at - " + jwtUtil.getIssuedAt(token));
                log.debug(":::::::: Token expiring at - " + jwtUtil.getExpiry(token));
                CustomUserDetails userDetails = (CustomUserDetails) userService.loadUserByUsername(usernameFromToken);
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), null, userDetails.getAuthorities());
                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                log.debug(":::::::: Setting Authentication...");
                SecurityContextHolder.getContext().setAuthentication(auth);
                log.debug(":::::::: Authentication set - {}", auth);
            }
        } catch (Exception e){
            log.debug(":::::::: Something went wrong while authenticating with JWT.");
        }
        filterChain.doFilter(request, response);
    }


    private String getTokenFromRequest(HttpServletRequest request) {

        String token = request.getHeader("Authorization");
        if(token != null && token.startsWith("Bearer ")){
            log.debug(":::::::: Token provided is well-formed");
            return token.substring(7);
        }

        log.debug(":::::::: Token provided is not well-formed");
        return null;
    }
}
