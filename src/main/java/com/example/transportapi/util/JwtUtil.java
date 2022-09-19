package com.example.transportapi.util;

import com.example.transportapi.security.CustomUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    public String generateToken(Authentication authentication){
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        Date now = new Date();
        Date expiry = new Date(now.getTime() + 3600000);

        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, "secret")
                .setSubject(authentication.getName())
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiry)
                .compact();
    }

    public String getUsernameFromToken(String token){
        Claims claims = Jwts.parser()
                .setSigningKey("secret")
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey("secret")
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e){
            throw new RuntimeException("Invalid JWT token was provided.", e.getCause());
        }

    }
}
