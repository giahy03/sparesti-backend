package edu.ntnu.idatt2106.sparesti.dto.user;

import lombok.Data;
import lombok.NonNull;

/**
 * Data Transfer Object (DTO) containing the user's old and new password.
 *
 * @author Ramtin Samavat
 * @version 1.0
 */
@Data
public class PasswordChangeDto {
  @NonNull
  private String oldPassword;
  @NonNull
  private String newPassword;
}
