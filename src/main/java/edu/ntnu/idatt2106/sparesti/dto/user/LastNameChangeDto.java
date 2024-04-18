package edu.ntnu.idatt2106.sparesti.dto.user;

import lombok.Data;
import lombok.NonNull;

@Data
public class LastNameChangeDto {
  @NonNull
  private String newLastName;
}
