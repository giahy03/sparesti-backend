package edu.ntnu.idatt2106.sparesti.model;

import edu.ntnu.idatt2106.sparesti.model.challenge.util.ChallengeUtility;
import edu.ntnu.idatt2106.sparesti.model.email.EmailCode;
import edu.ntnu.idatt2106.sparesti.model.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * A test class for the EmailCode class.
 *
 * @version 1.0
 * @see EmailCode
 * @author Jeffrey Yaw Annor Tabiri
 */
class EmailCodeTest {

  EmailCode emailCode;

  User user;

  @BeforeEach
  void setUp() {
    user = ChallengeUtility.createUserA();
    emailCode = ChallengeUtility.createEmailCodeA();
  }

  @Test
  void EmailCode_GetEmail_ReturnEmail() {
    //Arrange
    String expected = "example@guide";

    //Act
    String actual = emailCode.getEmail();

    //Assert
    assertEquals(expected, actual);
  }

  @Test
  void EmailCode_GetVerificationCode_ReturnVerificationCode() {
    //Arrange
    String expected = "123456";

    //Act
    String actual = emailCode.getVerificationCode();

    //Assert
    assertEquals(expected, actual);
  }

  @Test
  void EmailCode_GetExpiryTimestamp_ReturnExpiryTimestamp() {
    //Arrange
    LocalDateTime expected = LocalDateTime.of(2021, 12, 31, 23, 59, 59);

    //Act
    LocalDateTime actual = emailCode.getExpiryTimestamp();

    //Assert
    assertEquals(expected, actual);
  }

  @Test
  void EmailCode_NoArgsConstructor_ReturnEmailCode() {
    //Arrange
    EmailCode emailCode = new EmailCode();

    //Assert
    assertNotNull(emailCode);
  }

}