package edu.ntnu.idatt2106.sparesti.validation.validators;

import edu.ntnu.idatt2106.sparesti.validation.rules.UserValidationRules;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * The class provides specific validation for different types of user information.
 * The class extends {@link SimpleValidator}.
 * <p>
 * The code is inspired by Ramtin Samavat's GitHub repository: <a href="https://github.com/RamtinS/quiz-app-backend/blob/main/src/main/java/edu/ntnu/idatt2105/quizapp/validation/validators/UserValidator.java">...</a>
 * </p>
 *
 * @author Ramtin Samavat
 * @version 1.0
 */
public class UserValidator extends SimpleValidator {

  private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

  /**
   * The method validates the password based on predefined rules.
   *
   * @param password The password to be validated.
   * @throws IllegalArgumentException if the password is null, blank, or invalid.
   */
  public static void validatePassword(String password) throws IllegalArgumentException {
    if (!isNotNullOrBlank(password) || !password.matches(UserValidationRules.PASSWORD.getRegex())) {
      throw new IllegalArgumentException("The password must consist of at least 8 characters.");
    }
  }

  /**
   * The method validates a change of password based on predefined rules
   * and checks if the old password matches the password stored in the database.
   *
   * @param passwordFromDatabase The password retrieved from the database for comparison.
   * @param oldPassword The old password provided by the user.
   * @param newPassword The new password provided by the user.
   * @throws IllegalArgumentException If the old password was incorrect.
   */
  public static void validatePasswordChange(String passwordFromDatabase, String oldPassword,
                                            String newPassword) throws IllegalArgumentException {

    if (!passwordEncoder.matches(oldPassword, passwordFromDatabase)) {
      throw new IllegalArgumentException("Old password was incorrect.");
    }
    validatePassword(newPassword);
  }

  /**
   * The method validates the email based on predefined rules.
   *
   * @param email The email to be validated.
   * @throws IllegalArgumentException if the email is null, blank, or invalid.
   */
  public static void validateEmail(String email) throws IllegalArgumentException {
    if (!isNotNullOrBlank(email) || !email.matches(UserValidationRules.EMAIL.getRegex())) {
      throw new IllegalArgumentException("Invalid email format.");
    }
  }

  /**
   * The method validates the first name based on predefined rules.
   *
   * @param firstName The first name to be validated.
   * @throws IllegalArgumentException if the firstName is null, blank, or invalid.
   */
  public static void validateFirstName(String firstName) throws IllegalArgumentException {
    if (!isNotNullOrBlank(firstName) || !firstName
            .matches(UserValidationRules.FIRSTNAME.getRegex())) {

      throw new IllegalArgumentException("First name must be 1-64 characters "
              + "and contain only letters.");
    }
  }

  /**
   * The method validates the last name based on predefined rules.
   *
   * @param lastName The last name to be validated.
   * @throws IllegalArgumentException if the lastName is null, blank, or invalid.
   */
  public static void validateLastName(String lastName) throws IllegalArgumentException {
    if (!isNotNullOrBlank(lastName) || !lastName.matches(UserValidationRules.LASTNAME.getRegex())) {
      throw new IllegalArgumentException("Last name must be 1-64 characters"
              + " and contain only letters.");
    }
  }

  /**
   * The method validates that the income is not less than zero.
   *
   * @param income The income to be validated.
   * @throws IllegalArgumentException If the income is negative.
   */
  public static void validateIncome(double income) throws IllegalArgumentException {
    if (income < 0) {
      throw new IllegalArgumentException("Income cannot be negative.");
    }
  }
}
