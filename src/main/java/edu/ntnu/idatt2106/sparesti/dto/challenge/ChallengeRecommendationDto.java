package edu.ntnu.idatt2106.sparesti.dto.challenge;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Represents a challenge recommendation.
 */
@Data
@AllArgsConstructor
public class ChallengeRecommendationDto {
  private String goalDescription;
  private LocalDate startDate;
  private LocalDate endDate;
  private Double dailyAmount;
  private String category;
}
