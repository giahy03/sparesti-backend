package edu.ntnu.idatt2106.sparesti.dto.challenge;

import edu.ntnu.idatt2106.sparesti.model.challenge.Progress;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * Dto for previewing a shared challenge.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SharedChallengePreviewDto {
  private String firstName;
  private String lastName;

  private Long id;

  @NonNull
  private String title;

  @NonNull
  private String description;

  @NonNull
  private LocalDate startDate;

  @NonNull
  private LocalDate endDate;

  @NonNull
  private String difficulty;

  @NonNull
  private Progress progress;


}