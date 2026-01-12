package com.makeup.auth.security;

import com.makeup.auth.model.User;
import com.makeup.auth.model.Role;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtProvider {

    private final String SECRET = "secret-key";

    public String generateToken(User user) {
        return Jwts.builder()
                .claim("userId", user.getId())
                .claim("roles", user.getRoles().stream()
                        .map(Role::getName).toList())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000))
                .signWith(Keys.hmacShaKeyFor(SECRET.getBytes()))
                .compact();
    }
}
