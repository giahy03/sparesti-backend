package edu.ntnu.idatt2106.sparesti.service.email;

import edu.ntnu.idatt2106.sparesti.dto.email.EmailDetailsDto;
import edu.ntnu.idatt2106.sparesti.model.email.EmailCode;

import java.time.LocalDateTime;

public class EmailVerificationUtility {

  public static EmailCode createVerificationCodeA(String email) {
    return EmailCode.builder()
            .email(email)
            .verificationCode("123456")
            .expiryTimestamp(LocalDateTime.now().plusMinutes(5))
            .build();
  }

  public static EmailCode createVerificationCodeB(String email) {
    return EmailCode.builder()
            .email(email)
            .verificationCode("123456")
            .expiryTimestamp(LocalDateTime.now().minusMinutes(5))
            .build();
  }

  public static EmailDetailsDto createEmailDetailsDto() {
    return EmailDetailsDto.builder()
            .subject("Test subject")
            .body("Test body")
            .recipient("Test recipient")
            .build();
  }
}