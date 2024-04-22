package edu.ntnu.idatt2106.sparesti.service.auth;

import edu.ntnu.idatt2106.sparesti.model.user.User;
import io.github.cdimascio.dotenv.Dotenv;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import java.util.function.Function;
import javax.crypto.SecretKey;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

/**
 * Service class for handling JSON Web Token (JWT) operations.
 * <p>
 * The code is inspired by Ramtin Samavat's GitHub repository: <a href="https://github.com/RamtinS/quiz-app-backend/blob/main/src/main/java/edu/ntnu/idatt2105/quizapp/services/JwtService.java">...</a>
 * </p>
 *
 * @author Jeffrey Tabiri
 * @author Ramtin Samavat
 * @version 1.0
 */
@Slf4j
@Service
public class JwtService {

  // 512 bits encryption key.
  private static final String SECRET_KEY = Dotenv.load().get("JWT_SECRET");

  /**
   * Extract username from JWT token.
   *
   * @param token The JWT token to extract the username from.
   * @return The username associated with the token.
   */
  public String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  /**
   * Extracts a claim from the JWT token.
   *
   * @param token The JWT token to extract the claim from.
   * @param claimsResolver The function to resolve the claim from the extracted JWT claims.
   * @param <T> The type of the claim to extract.
   * @return The extracted claim.
   */
  public <T> T extractClaim(String token, @NonNull Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  /**
   * Generates a JSON Web Token (JWT) for the specified user.
   *
   * @param user The user for whom to generate the JWT token.
   * @return A string representing the JWT.
   * @throws NullPointerException if the user parameter is null.
   */
  public String generateToken(@NonNull User user) throws NullPointerException {
    return Jwts.builder()
            .subject(user.getUsername())
            .issuedAt(new Date(System.currentTimeMillis()))
            .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
            .signWith(getSignKey())
            .compact();
  }

  /**
   * Check to see if the token is valid.
   *
   * @param token Token to be checked
   * @param userDetails Containing a relationship to a user entity object.
   * @return True if valid, false otherwise.
   */
  public boolean isTokenValid(String token, UserDetails userDetails) {
    final String username = extractUsername(token);
    return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
  }

  /**
   * Checks if given token is expired.
   *
   * @param token the token to check.
   * @return true if expired, false if not.
   */
  private boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  /**
   * Extract the expiration date from a JWT token.
   *
   * @param token The token to extract its expiration date from.
   * @return Date object representing the expiration of a token.
   */
  private Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  /**
   * A method to extract all claims regarding a specific token.
   *
   * @param token The token in which the claims are to be extracted out off.
   * @return A set of claims regarding a token.
   */
  private Claims extractAllClaims(String token) {
    return Jwts.parser()
            .verifyWith(getSignKey())
            .build()
            .parseSignedClaims(token)
            .getPayload();
  }

  /**
   * Retrieves the signing key used to sign JWT tokens.
   *
   * @return A {@link SecretKey} object representing the signing key.
   */
  private SecretKey getSignKey() {
    byte[] keyBytes = Decoders.BASE64URL.decode(SECRET_KEY);
    return Keys.hmacShaKeyFor(keyBytes);
  }
}