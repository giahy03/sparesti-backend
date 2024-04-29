package edu.ntnu.idatt2106.sparesti.dto.challenge;

import edu.ntnu.idatt2106.sparesti.model.challenge.Progress;
import lombok.Builder;
import lombok.Data;

/**
 * Represents a challenge update request dto.
 *
 * @author Jeffrey Yaw Annor Tabiri
 * @version 1.0
 */
@Data
@Builder
public class ChallengeUpdateRequestDto {
  Progress progress;
}