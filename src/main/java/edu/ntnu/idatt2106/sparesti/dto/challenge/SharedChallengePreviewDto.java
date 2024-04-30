package edu.ntnu.idatt2106.sparesti.dto.challenge;

import edu.ntnu.idatt2106.sparesti.model.challenge.Progress;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

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