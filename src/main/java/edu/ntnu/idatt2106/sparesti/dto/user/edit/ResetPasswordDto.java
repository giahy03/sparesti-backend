package edu.ntnu.idatt2106.sparesti.dto.user.edit;

import lombok.Data;
import lombok.NonNull;

/**
 * Data Transfer Object (DTO) containing information required for resetting a user's password.
 *
 * @author Ramitn Samavat
 * @version 1.0
 */
@Data
public class ResetPasswordDto {
  @NonNull
  private String email;
  @NonNull
  private String emailVerificationCode;
  @NonNull
  private String newPassword;
}
