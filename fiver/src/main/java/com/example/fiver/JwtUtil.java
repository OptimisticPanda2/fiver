package com.example.fiver;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
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
     public String extractEmail(String token)
     {
         return Jwts.parserBuilder()
                 .setSigningKey(key)
                 .build()
                 .parseClaimsJws(token)
                 .getBody()
                 .getSubject();
     }
    public boolean isTokenExpired(String token)
    {
        Date expiration = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
        return expiration.before(new Date());
    }
     public boolean validateToken(String token,UserDetails userDetails)
     {
         String extractedEmail = extractEmail(token);
         return (extractedEmail.equals(userDetails.getUsername()))&&!isTokenExpired(token);
     }
}
