package com.profilerenovation.security.jwt;

import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import com.profilerenovation.security.user.ProfileUserDetails;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    private String jwtSecret;
    private int jwtExpirationTime;

    @Autowired
    public JwtUtils(@Value("${security.jwt.secret}") String jwtSecret,
            @Value("${security.jwt.expirationTime}") int jwtExpirationTime) {
        this.jwtSecret = jwtSecret;
        this.jwtExpirationTime = jwtExpirationTime;
    }

    public String generateJwtTokenForUser(Authentication authentication) {
        ProfileUserDetails userPrincipal = (ProfileUserDetails) authentication.getPrincipal();
        List<String> roles = userPrincipal.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        // Agregar registros de depuración
        System.out.println("Generating JWT for user: " + userPrincipal.getUsername());
        System.out.println("Roles: " + roles);

        return Jwts.builder()
                .setSubject(userPrincipal.getUsername())
                .claim("roles", roles)
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + jwtExpirationTime))
                .signWith(key(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String getUserNameFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public List<String> getRolesFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("roles", List.class);
    }

    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            logger.error("Expired token: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("Unsupported token: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("Malformed token: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("Illegal argument: {}", e.getMessage());
        } catch (Exception e) {
            logger.error("Exception: {}", e.getMessage());
        }
        return false;
    }
}
