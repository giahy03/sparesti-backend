package edu.ntnu.idatt2106.sparesti.validation.validators;

import edu.ntnu.idatt2106.sparesti.validation.rules.UserValidationRules;

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

  /**
   * The method validates the password based on predefined rules.
   *
   * @param password The password to be validated.
   * @throws IllegalArgumentException if the password is null, blank, or invalid.
   */
  public static void validatePassword(String password) {
    if (!isNotNullOrBlank(password) || !password.matches(UserValidationRules.PASSWORD.getRegex())) {
      throw new IllegalArgumentException("The password must consist of at least 8 characters.");
    }
  }

  /**
   * The method validates the email based on predefined rules.
   *
   * @param email The email to be validated.
   * @throws IllegalArgumentException if the email is null, blank, or invalid.
   */
  public static void validateEmail(String email) {
    if (!isNotNullOrBlank(email) || !email.matches(UserValidationRules.EMAIL.getRegex())) {
      throw new IllegalArgumentException("Invalid email format.");
    }
  }

  /**
   * The method validates the name based on predefined rules.
   *
   * @param name The name to be validated.
   * @throws IllegalArgumentException if the name is null, blank, or invalid.
   */
  public static void validateName(String name) {
    if (!isNotNullOrBlank(name) || !name.matches(UserValidationRules.FIRSTNAME.getRegex())) {
      throw new IllegalArgumentException("First name must be 1-64 characters "
              + "and contain only letters.");
    }
  }

  /**
   * The method validates the surname based on predefined rules.
   *
   * @param surname The surname to be validated.
   * @throws IllegalArgumentException if the surname is null, blank, or invalid.
   */
  public static void validateSurname(String surname) {
    if (!isNotNullOrBlank(surname) || !surname.matches(UserValidationRules.LASTNAME.getRegex())) {
      throw new IllegalArgumentException("Last name must be 1-64 characters"
              + " and contain only letters.");
    }
  }
}
