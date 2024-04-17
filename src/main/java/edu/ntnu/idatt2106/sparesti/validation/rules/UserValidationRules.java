package edu.ntnu.idatt2106.sparesti.validation.rules;

import lombok.Getter;

/**
 * Enum containing validation rules for user attributes, such as password,
 * email address, first name, and last name.
 * <p>
 * The code is inspired by Ramtin Samavat's GitHub repository: <a href="https://github.com/RamtinS/quiz-app-backend/blob/main/src/main/java/edu/ntnu/idatt2105/quizapp/validation/rules/UserValidationRules.java">...</a>
 * </p>
 *
 * @author Ramtin Samavat
 * @version 1.0
 */
@Getter
public enum UserValidationRules {

  /**
   * Regular expression for validating passwords.
   * The password must be at least 8 characters long.
   */
  PASSWORD("^.{8,}$"),

  /**
   * Regular expression for validating email addresses.
   * The email address must follow the standard format and be between 2 and 128 characters.
   */
  EMAIL("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,128}$"),

  /**
   * Regular expression for validating names.
   * The first name can only contain letters and must be between 1 and 64 characters long.
   */
  FIRSTNAME("^[a-zA-Z]{1,64}$"),

  /**
   * Regular expression for validating surnames.
   * The last name can only contain letters and must be between 1 and 64 characters long.
   */
  LASTNAME("^[a-zA-Z]{1,64}$");

  private final String regex;

  /**
   * Constructor for UserValidationRules enum.
   *
   * @param regex The regular expression associated with the rule.
   */
  UserValidationRules(String regex) {
    this.regex = regex;
  }
}
