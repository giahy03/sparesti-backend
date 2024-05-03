package edu.ntnu.idatt2106.sparesti.model.user.util;

import edu.ntnu.idatt2106.sparesti.dto.user.UserDetailsDto;
import edu.ntnu.idatt2106.sparesti.dto.user.edit.PasswordChangeDto;
import edu.ntnu.idatt2106.sparesti.model.analysis.ssb.SsbLivingStatus;

/**
 * Utility class that creates objects to support testing classes.
 *
 * @author Hanne-Sofie Søreide
 */
public class UserUtility {


  /**
   * Creates a password change dto.
   *
   * @return a password change dto.
   */
  public static PasswordChangeDto createPasswordChangeDto() {

    return new PasswordChangeDto("abc", "123");
  }

  /**
   * Creates a json string of a password change dto.
   *
   * @return a json string of a password change dto.
   */
  public static String createPasswordChangeDtoJson() {
    return "{"
            + "\"oldPassword\":\"abc\","
            + "\"newPassword\":\"123\""
            + "}";
  }

  /**
   * Creates a json string of a first name change dto.
   *
   * @return a json string of a first name change dto.
   */
  public static String createFirstNameChangeDtoJson() {
    return "{"
            + "\"newFirstName\":\"Anne\""
            + "}";
  }

  /**
   * Creates a json string of a last name change dto.
   *
   * @return a json string of a last name change dto.
   */
  public static String createLastNameChangeDtoJson() {
    return "{"
            + "\"newLastName\":\"Larsen\""
            + "}";
  }

  /**
   * Creates a json string of an income change dto.
   *
   * @return a json string of an income change dto.
   */
  public static String createIncomeChangeDtoJson() {
    return "{"
            + "\"newIncome\":50000.0"
            + "}";
  }

  /**
   * Creates a json string of a living status change dto.
   *
   * @return a json string of a living status change dto.
   */
  public static String createLivingStatusChangeDtoJson() {
    return "{"
            + "\"newLivingStatus\":2"
            + "}";
  }

  /**
   * Creates a json string of a user details dto.
   *
   * @return a json string of a user details dto.
   */
  public static String createUserDetailsDtoJson() {
    String livingStatus = SsbLivingStatus.fromInteger(2).getStatus();
    return "{"
            + "\"firstName\":\"Ole\","
            + "\"lastName\":\"Hansen\","
            + "\"income\":50000.0,"
            + "\"livingStatus\":\"LivingStatus\""
            + "}";
  }

  /**
   * Creates a user details dto.
   *
   * @return a user details dto.
   */
  public static UserDetailsDto createUserDetailsDto() {
    return UserDetailsDto.builder()
            .firstName("Ole")
            .lastName("Hansen")
            .income(50000.0)
            .livingStatus("LivingStatus")
            .savingPercentage(20)
            .build();
  }

  /**
   * Creates a json string of a user info dto.
   *
   * @return a json string of a user info dto.
   */
  public static String createUserInfoDtoJson() {
    return "{"
            + "\"income\":50000.0,"
            + "\"livingStatus\":2"
            + "}";
  }

  /**
   * Creates a json string of a reset password dto.
   *
   * @return a json string of a reset password dto.
   */
  public static String createResetPasswordDtoJson() {
    return "{"
            + "\"email\":\"abc@email.com\","
            + "\"emailVerificationCode\":\"CODE\","
            + "\"newPassword\":\"long-password\""
            + "}";
  }
}
