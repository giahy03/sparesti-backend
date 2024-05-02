package edu.ntnu.idatt2106.sparesti.dto.saving;

import lombok.Builder;
import lombok.Data;

/**
 * DTO representing a user contributing to a given goal as well
 * as the amount the user has contributed with thus far.
 *
 * @author Hanne-Sofie Søreide
 */

@Data
@Builder
public class SavingGoalContributorDto {
  private String firstName;
  private String lastName;
  private String email;
  private double contributedAmount;
}
