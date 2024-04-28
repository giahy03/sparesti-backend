package edu.ntnu.idatt2106.sparesti.dto.challenge;

import edu.ntnu.idatt2106.sparesti.model.challenge.Progress;
import lombok.Builder;

@Builder
public class SharedChallengePreviewDto {
  private String firstName;
  private String lastName;
  private Progress progress;
}