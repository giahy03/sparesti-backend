package edu.ntnu.idatt2106.sparesti.dto.challenge;

import lombok.Data;

/**
 * Represents a preview of a challenge.
 * The preview contains the id, title, lives and difficulty of the challenge.
 *
 * @author Jeffrey Yaw Annor Tabiri
 * @version 1.0
 */
@Data
public class ChallengePreviewDto {
  private Long id;
  private String title;
  private int lives;
  private String difficulty;
}
