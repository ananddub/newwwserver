package com.example.wwwserver.securtiy;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;

import java.util.Date;
import java.util.Map;

public class JwtUtil {
    private static final long EXPIRATION_TIME = 864_000_000;

    public static String generateToken(Map<String, Object> object,String SECRET) {
        try {
            String objectJson = new ObjectMapper().writeValueAsString(object);
             return Jwts.builder()
                    .setSubject(objectJson)
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                    .signWith(SignatureAlgorithm.HS512, SECRET)
                    .compact();
        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static Map<String, Object> extractObject(String token,String SECRET) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token)
                    .getBody();

            String objectJson = claims.getSubject();
            return new ObjectMapper().readValue(objectJson, Map.class );
        } catch (SignatureException e) {
            throw new RuntimeException("Invalid token signature", e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error while converting JSON to object", e);
        } catch (Exception e) {
            throw new RuntimeException("Error while extracting object from token", e);
        }
    }

    public static boolean isTokenExpired(String token,String SECRET) {
        try {
            Date expirationDate = Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token)
                    .getBody()
                    .getExpiration();
            return expirationDate.before(new Date());
        } catch (Exception e) {
            throw new RuntimeException("Error while checking token expiration", e);
        }
    }

    public static boolean validateToken(String token,String SECRET) {
        return !isTokenExpired(token,SECRET);
    }
}
