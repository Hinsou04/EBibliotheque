package Labo.Academie.security;

import Labo.Academie.modele.Membre;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {

    private static final String SECRET_KEY = "mySuperSecretKeyForJwtGenerationThatMustBeVeryLong123456";

    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 24; // 24h

    public String generateToken(Membre membre) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", membre.getRole());
        claims.put("email", membre.getEmail());

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(membre.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    public boolean isTokenValid(String token, Membre membre) {
        String username = extractUsername(token);
        return (username.equals(membre.getEmail())) && !isTokenExpired(token);
    }

    public boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }
}
