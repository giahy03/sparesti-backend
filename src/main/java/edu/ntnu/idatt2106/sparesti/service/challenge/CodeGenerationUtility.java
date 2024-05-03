package edu.ntnu.idatt2106.sparesti.service.challenge;

import java.security.SecureRandom;

/**
 * Utility class for generating codes for various operations.
 *
 * @version 1.0
 * @author Jeffrey Yaw Annor Tabiri
 */
public class CodeGenerationUtility {

  private CodeGenerationUtility() {
    throw new IllegalStateException("Utility class");
  }

  private static final String CHARACTERS =
          """
          abcdefghijklmnopqrstuvwxyz
          ABCDEFGHIJKLMNOPQRSTUVWXYZ
          0123456789
          """;
  private static final int LENGTH = 8;
  private static final SecureRandom random = new SecureRandom();

  /**
   * Generates a verification token.
   * The token is a random string of length 6.
   *
   * @return the generated token.
   */
  public static String generateJoinCode() {
    StringBuilder token = new StringBuilder(LENGTH);
    for (int i = 0; i < LENGTH; i++) {
      token.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
    }
    return token.toString();
  }
}