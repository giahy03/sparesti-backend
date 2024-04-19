package edu.ntnu.idatt2106.sparesti.service.auth;

import edu.ntnu.idatt2106.sparesti.model.challenge.util.ChallengeUtility;
import edu.ntnu.idatt2106.sparesti.model.user.User;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for JwtService
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
  void JwtService_ExtractClaim_ReturnClaim() {
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
  void JwtService_GenerateToken_ReturnToken() {
    //Act
    String token = jwtService.generateToken(testUser);

    //Assert
    assertNotNull(token);
    assertFalse(token.isBlank());
    assertFalse(token.isEmpty());
  }

  @Test
  void JwtService_isTokenValid_ReturnTrue() {
    //Arrange
    String generatedToken = jwtService.generateToken(testUser);

    //Act
    boolean isTokenValid = jwtService.isTokenValid(generatedToken, testUser);

    //Assert
    assertTrue(isTokenValid);
  }

  @Test
  void JwtService_isTokenValid_ReturnFalse() {
    //Arrange
    String generatedToken = jwtService.generateToken(testUser);
    User differentUser = ChallengeUtility.createUserB();

    //Act
    boolean isTokenValid = jwtService.isTokenValid(generatedToken, differentUser);

    //Assert
    assertFalse(isTokenValid);
  }

  @Test
  void JwtService_ExtractUsername_ReturnUsername() {
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