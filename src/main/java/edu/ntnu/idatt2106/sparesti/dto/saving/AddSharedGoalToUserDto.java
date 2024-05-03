package edu.ntnu.idatt2106.sparesti.dto.saving;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Dto for adding a shared goal to a user.
 *
 * @author Hanne-Sofie Søreide
 */
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddSharedGoalToUserDto {
  private String joinCode;
}
