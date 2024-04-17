package edu.ntnu.idatt2106.sparesti.dto.challenge;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import java.time.LocalDate;


/**
 * Represents a challenge dto superclass.
 *
 * @author Jeffrey Yaw Annor Tabiri
 * @version 1.0
 */
@Getter
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "challengeType")
@JsonSubTypes({
        @JsonSubTypes.Type(value = SavingChallengeDto.class, name = "SavingChallengeDto")}
)

public class ChallengeDto {
  private String title;
  private int lives;
  private int currentTile;
  private LocalDate startDate;
  private LocalDate endDate;
  private String difficulty;
}