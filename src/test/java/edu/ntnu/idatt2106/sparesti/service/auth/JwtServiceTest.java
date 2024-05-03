package edu.ntnu.idatt2106.sparesti.service.auth;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.ntnu.idatt2106.sparesti.model.challenge.util.ChallengeUtility;
import edu.ntnu.idatt2106.sparesti.model.user.User;
import io.jsonwebtoken.Claims;
import java.util.function.Function;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


/**
 * Test class for JwtService.
 *
 * @version 1.0
 * @see JwtService
 * @author Jeffrey Yaw Annor Tabiri
 */
class JwtServiceTest {
  static JwtService jwtService;
  static User testUser;
  static String testToken;

  @BeforeAll
  static void beforeAll() {
    testUser = ChallengeUtility.createUserA();
    jwtService = new JwtService();
    testToken = jwtService.generateToken(testUser);
  }

  @Test
  @DisplayName("Service extract claim should return claim")
  void jwtService_ExtractClaim_ReturnClaim() {
    //Arrange
    String expected = testUser.getUsername();
    String token = jwtService.generateToken(testUser);

    //Act
    Function<Claims, String> resolver = Claims::getSubject;
    String actual = jwtService.extractClaim(token, resolver);

    //Assert
    assertEquals(expected, actual);
  }

  @Test
  @DisplayName("Service extract claim should return null")
  void jwtService_GenerateToken_ReturnToken() {
    //Act
    String token = jwtService.generateToken(testUser);

    //Assert
    assertNotNull(token);
    assertFalse(token.isBlank());
    assertFalse(token.isEmpty());
  }

  @Test
  @DisplayName("Service extract claim should return null")
  void jwtService_isTokenValid_ReturnTrue() {
    //Arrange
    String generatedToken = jwtService.generateToken(testUser);

    //Act
    boolean isTokenValid = jwtService.isTokenValid(generatedToken, testUser);

    //Assert
    assertTrue(isTokenValid);
  }

  @Test
  @DisplayName("Service extract claim should return null")
  void jwtService_isTokenValid_ReturnFalse() {
    //Arrange
    String generatedToken = jwtService.generateToken(testUser);
    User differentUser = ChallengeUtility.createUserC();

    //Act
    boolean isTokenValid = jwtService.isTokenValid(generatedToken, differentUser);

    //Assert
    assertFalse(isTokenValid);
  }

  @Test
  @DisplayName("Service extract claim should return null")
  void jwtService_ExtractUsername_ReturnUsername() {
    //Arrange
    String expected = testUser.getUsername();
    String token = jwtService.generateToken(testUser);

    //Act
    Function<Claims, String> resolver = Claims::getSubject;
    String actual = jwtService.extractUsername(token);

    //Assert
    assertEquals(expected, actual);
  }

}