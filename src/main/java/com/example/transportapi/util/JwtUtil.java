package com.example.transportapi.util;

import com.example.transportapi.security.CustomUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class JwtUtil {

    public String generateToken(Authentication authentication){
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        log.debug(":::::::: Generating token for user ["+ username +"]");
        Date now = new Date();
        Date expiry = new Date(now.getTime() + 3600000);

        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, "secret")
                .setSubject(authentication.getName())
//                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiry)
                .compact();
    }

    public String getUsernameFromToken(String token){
        log.debug(":::::::: Extracting username from token...");
        Claims claims = Jwts.parser()
                .setSigningKey("secret")
                .parseClaimsJws(token)
                .getBody();
        log.debug(":::::::: Username extracted from token - {}", claims.getSubject());
        return claims.getSubject();
    }

    public String getIssuedAt(String token){
        Date issuedAt = Jwts.parser()
                .setSigningKey("secret")
                .parseClaimsJws(token)
                .getBody()
                .getIssuedAt();
        return issuedAt.toString();
    }

    public String getExpiry(String token){
        Date expiration = Jwts.parser()
                .setSigningKey("secret")
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
        return expiration.toString();
    }

    public boolean validateToken(String token) {
        try {
            log.debug(":::::::: Validating token...");
            Jwts.parser()
                    .setSigningKey("secret")
                    .parseClaimsJws(token);
            log.debug(":::::::: Token is validated");
            return true;
        } catch (Exception e){
            log.debug(":::::::: Error validating the token.");
        }
        return false;
    }
}
