package com.example.scryp;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    private final Key SECRET_KEY = Keys.hmacShaKeyFor(
            "abcdefghijklmnopabcdefghijklmnop".getBytes()
    );

    public String generateToken(String email)
    {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(
                        new Date(
                                System.currentTimeMillis()
                                        + 1000 * 60 * 60
                        )
                )
                .signWith(
                        SignatureAlgorithm.HS256,
                        SECRET_KEY
                )
                .compact();
    }

    public String extractEmail(String token)
    {
        return extractClaims(token).getSubject();
    }

    public Claims extractClaims(String token)
    {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean isTokenExpired(String token)
    {
        Date expiration =
                extractClaims(token).getExpiration();

        return expiration.before(new Date());
    }

    public boolean validateToken(
            String token,
            UserDetails userDetails)
    {
        String extractedEmail =
                extractEmail(token);

        return extractedEmail.equals(
                userDetails.getUsername()
        ) && !isTokenExpired(token);
    }
}