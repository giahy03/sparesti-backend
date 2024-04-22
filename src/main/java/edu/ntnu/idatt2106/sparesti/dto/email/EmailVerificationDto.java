package edu.ntnu.idatt2106.sparesti.dto.email;

import lombok.Data;

/**
 * Data Transfer Object (DTO) containing email verification information.
 *
 * @author Ramtin Samavat
 * @version 1.0
 */
@Data
public class EmailVerificationDto {
  private String email;
  private String verificationCode;
}