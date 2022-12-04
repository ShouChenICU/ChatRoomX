package com.mystery.chat.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.GrantedAuthority;

import java.security.Key;
import java.util.Collection;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author shouchen
 * @date 2022/12/3
 */
public class JWTUtils {
    private static final Key JWT_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS384);
    private static final long JWT_EXPIRE_OFFSET = TimeUnit.MINUTES.toMillis(30);

    public static String genToken(String uid, Collection<? extends GrantedAuthority> authorities) {
        Objects.requireNonNull(authorities);
        String auth = authorities
                .stream()
                .map(GrantedAuthority::getAuthority)
                .reduce((a, b) -> a + "," + b)
                .orElse("");
        return Jwts.builder()
                .setClaims(Jwts.claims()
                        .setAudience(uid)
                        .setSubject(auth)
                )
                .signWith(JWT_KEY)
                .setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRE_OFFSET))
                .compact();
    }

    public static Claims parseToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(JWT_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
