package edu.ntnu.idatt2106.sparesti.dto.challenge;

import edu.ntnu.idatt2106.sparesti.model.challenge.Progress;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a challenge update request dto.
 *
 * @author Jeffrey Yaw Annor Tabiri
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChallengeUpdateRequestDto {
  Progress progress;
}