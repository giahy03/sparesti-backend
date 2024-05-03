package edu.ntnu.idatt2106.sparesti.service.email;

import edu.ntnu.idatt2106.sparesti.dto.email.EmailDetailsDto;
import edu.ntnu.idatt2106.sparesti.model.email.EmailCode;
import java.time.LocalDateTime;


/**
 * A utility class for creating objects used in the EmailVerificationServiceTest class.
 *
 * @version 1.0
 * @author Jeffrey Yaw Annor Tabiri
 */
public class EmailVerificationUtility {

  /**
   * Creates a verification code for a given email.
   *
   * @param email the email to create the verification code for
   * @return the created verification code
   */
  public static EmailCode createVerificationCodeA(String email) {
    return EmailCode.builder()
            .email(email)
            .verificationCode("123456")
            .expiryTimestamp(LocalDateTime.now().plusMinutes(5))
            .build();
  }

  /**
   * Creates a verification code for a given email.
   *
   * @param email the email to create the verification code for
   * @return the created verification code
   */
  public static EmailCode createVerificationCodeB(String email) {
    return EmailCode.builder()
            .email(email)
            .verificationCode("123456")
            .expiryTimestamp(LocalDateTime.now().minusMinutes(5))
            .build();
  }

  /**
   * Creates a verification code for a given email.
   *
   * @return the created verification code.
   */
  public static EmailDetailsDto createEmailDetailsDto() {
    return EmailDetailsDto.builder()
            .subject("Test subject")
            .body("Test body")
            .recipient("Test recipient")
            .build();
  }

}