package edu.ntnu.idatt2106.sparesti.dto.challenge;

import lombok.Data;
import lombok.NonNull;

/**
 * Represents a preview of a challenge.
 * The preview contains the id, title, lives and difficulty of the challenge.
 *
 * @author Jeffrey Yaw Annor Tabiri
 * @version 1.0
 */
@Data
public class ChallengePreviewDto {

  @NonNull
  private Long id;

  @NonNull
  private String title;

  @NonNull
  private String difficulty;

  private String joinCode;
}
