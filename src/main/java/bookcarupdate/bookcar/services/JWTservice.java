package bookcarupdate.bookcar.services;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;

public interface JWTservice {
    String extractUserName(String token);
    String generateToken(UserDetails userDetails);
    String generateRefreshToken(Map<String, Object> extractClaims, UserDetails userDetails);
    boolean isTokenValid(String jwt, UserDetails userDetails);
}
