package com.tensei.tasks.service.impl;

import com.tensei.tasks.domain.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${jwt.token.signing-key}")
    private String signingKey;

    /**
     * Generate JWT token
     *
     * @param userDetails user claims
     * @return token
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();

        if (userDetails instanceof User customUserDetails) {
            claims.put("id", customUserDetails.getId());
            claims.put("email",  customUserDetails.getEmail());
        }

        return Jwts.builder()
                .claims(claims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 100000 * 60 * 24))
                .signWith(getSigningKey())
                .compact();
    }

    /**
     * Checks if token is valid
     *
     * @param token token
     * @param userDetails user data
     * @return true if token valid, false otherwise
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        String usernameClaim = extractClaim(token, Claims::getSubject);
        return usernameClaim.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    /**
     * Extract claim
     *
     * @param token token
     * @param claimsResolver function to retrieve claim
     * @return claim
     * @param <T> claim data type
     */
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = Jwts.parser()
                .verifyWith((SecretKey) getSigningKey())
                .build()
                .parseSignedClaims(token).getPayload();

        return claimsResolver.apply(claims);
    }

    /**
     * Checks if token has been expired
     *
     * @param token token
     * @return true if token has been expired, false otherwise
     */
    private boolean isTokenExpired(String token) {
        Date expirationTime = extractClaim(token, Claims::getExpiration);
        return expirationTime.before(new Date());
    }

    /**
     * Retrieve key for signing the token
     *
     * @return Key
     */
    private Key getSigningKey() {
        byte[] decoded = Decoders.BASE64.decode(signingKey);
        return Keys.hmacShaKeyFor(decoded);
    }

}
