package com.example.fiver;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
public class JwtUtil {

    private Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
     public String generateToken(String email)
     {
         return Jwts.builder()
                 .setSubject(email)
                 .setIssuedAt(new Date())
                 .setExpiration(new Date(System.currentTimeMillis()+1000*60*60))
                 .signWith(key)
                 .compact();
     }
}
