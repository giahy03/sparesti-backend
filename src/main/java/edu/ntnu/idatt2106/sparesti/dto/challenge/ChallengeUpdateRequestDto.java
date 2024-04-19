package edu.ntnu.idatt2106.sparesti.dto.challenge;

import lombok.Data;

/**
 * Represents a challenge update request dto.
 *
 * @author Jeffrey Yaw Annor Tabiri
 * @version 1.0
 */
@Data
public class ChallengeUpdateRequestDto {
  int currentTiles;
  int lives;
}