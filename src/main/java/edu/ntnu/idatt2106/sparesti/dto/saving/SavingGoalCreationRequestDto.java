package edu.ntnu.idatt2106.sparesti.dto.saving;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

/**
 * DTO containing information to create a new saving goal
 *
 * @author Hanne-Sofie Søreide
 */
@Getter
@Data
@Builder
public class SavingGoalCreationRequestDto {
  @NotBlank
  private String goalName;
  private double totalAmount;
  private int lives;
  @NotNull
  private LocalDate startDate;
  @NotNull
  private LocalDate endDate;


}


