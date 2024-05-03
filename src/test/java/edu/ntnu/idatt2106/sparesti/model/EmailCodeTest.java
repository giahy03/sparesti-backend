package edu.ntnu.idatt2106.sparesti.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import edu.ntnu.idatt2106.sparesti.model.challenge.util.ChallengeUtility;
import edu.ntnu.idatt2106.sparesti.model.email.EmailCode;
import edu.ntnu.idatt2106.sparesti.model.user.User;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;




/**
 * A test class for the EmailCode class.
 *
 * @author Jeffrey Yaw Annor Tabiri
 * @version 1.0
 * @see EmailCode
 */
class EmailCodeTest {

  EmailCode emailCode;

  User user;

  @BeforeEach
  void setUp() {
    user = ChallengeUtility.createUserA();
    emailCode = ChallengeUtility.createEmailCodeA();
  }

  @DisplayName("Test for the getter methods of EmailCode")
  @Test
  void emailCode_GetEmail_ReturnEmail() {
    //Arrange
    String expected = "example@guide";

    //Act
    String actual = emailCode.getEmail();

    //Assert
    assertEquals(expected, actual);
  }

  @DisplayName("Test for the getter methods of EmailCode")
  @Test
  void emailCode_GetVerificationCode_ReturnVerificationCode() {
    //Arrange
    String expected = "123456";

    //Act
    String actual = emailCode.getVerificationCode();

    //Assert
    assertEquals(expected, actual);
  }

  @DisplayName("Test for the getter methods of EmailCode")
  @Test
  void emailCode_GetExpiryTimestamp_ReturnExpiryTimestamp() {
    //Arrange
    LocalDateTime expected = LocalDateTime.of(2021, 12, 31, 23, 59, 59);

    //Act
    LocalDateTime actual = emailCode.getExpiryTimestamp();

    //Assert
    assertEquals(expected, actual);
  }

  @DisplayName("Test for the getter methods of EmailCode")
  @Test
  void emailCode_NoArgsConstructor_ReturnEmailCode() {
    //Arrange
    EmailCode emailCode = new EmailCode();

    //Assert
    assertNotNull(emailCode);
  }

}