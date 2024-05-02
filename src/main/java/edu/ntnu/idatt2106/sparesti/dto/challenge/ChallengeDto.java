package edu.ntnu.idatt2106.sparesti.dto.challenge;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import edu.ntnu.idatt2106.sparesti.model.challenge.Progress;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;


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
    property = "challengeType",
    defaultImpl = ChallengeDto.class)
@JsonSubTypes({
    @JsonSubTypes.Type(value = SharedChallengeDto.class, name = "SharedChallengeDto"),
}
)
public class ChallengeDto {
  @NonNull
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