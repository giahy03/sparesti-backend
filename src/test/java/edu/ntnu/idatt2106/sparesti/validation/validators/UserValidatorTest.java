package edu.ntnu.idatt2106.sparesti.validation.validators;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * The class tests basic validation for user input.
 *
 * @version 1.0
 * @author Jeffrey Yaw Annor Tabiri
 */
class UserValidatorTest {

  @Test
  void UserValidator_ValidatePassword_ThrowsIllegalArgument() {
    // Arrange
    String password = "p";

    // Act & Assert
    assertThrows(IllegalArgumentException.class, () -> UserValidator.validatePassword(password));
  }

  @Test
  void UserValidator_ValidatePasswordChange_ShouldChangePassword() {
    // Arrange
    String passwordFromDatabase = "password";
    String oldPassword = "password";
    String newPassword = "newPassword";

    PasswordEncoder mockEncoder = mock(PasswordEncoder.class);
    UserValidator.setPasswordEncoder(mockEncoder);

    when(mockEncoder.matches(anyString(), anyString())).thenReturn(true);
    // Act & Assert
    UserValidator.validatePasswordChange(passwordFromDatabase, oldPassword, newPassword);
  }


  @Test
  void UserValidator_ValidatePasswordChange_ShouldReturnException() {
    // Arrange
    String passwordFromDatabase = "password";
    String oldPassword = "password";
    String newPassword = "newPassword";

    PasswordEncoder mockEncoder = mock(PasswordEncoder.class);
    UserValidator.setPasswordEncoder(mockEncoder);

    when(mockEncoder.matches(anyString(), anyString())).thenReturn(false);

    // Act & Assert
    assertThrows(IllegalArgumentException.class, () ->
            UserValidator.validatePasswordChange(passwordFromDatabase, oldPassword, newPassword));
  }

  @Test
  void UserValidator_ValidateEmail_ShouldReturnException() {
    // Arrange
    String email = "test@test";

    // Act & Assert
    assertThrows(IllegalArgumentException.class, () -> UserValidator.validateEmail(email));
  }

  @Test
  void UserValidator_Validate_ShouldReturn() {
    // Arrange
    String email = "test@test.com";
    String firstName = "Cody";
    String lastName = "Cody";
    double income = 1000.0;

    // Act & Assert
    UserValidator.validateEmail(email);
    UserValidator.validateFirstName(firstName);
    UserValidator.validateLastName(lastName);
    UserValidator.validateIncome(income);
  }

  @Test
  void UserValidator_Validate_ShouldReturnExceptions() {
    // Arrange
    String email = ".";
    String firstName = "1";
    String lastName = "2";
    double income = -1.0;

    // Act & Assert
    assertThrows(IllegalArgumentException.class, () -> {
      UserValidator.validateEmail(email);
    });
    assertThrows(IllegalArgumentException.class, () -> {
      UserValidator.validateFirstName(firstName);
    });
    assertThrows(IllegalArgumentException.class, () -> {
      UserValidator.validateLastName(lastName);
    });
    assertThrows(IllegalArgumentException.class, () -> {
      UserValidator.validateIncome(income);
    });

  }

}