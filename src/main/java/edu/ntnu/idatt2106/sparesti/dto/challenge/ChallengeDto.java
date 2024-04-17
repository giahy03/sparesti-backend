package edu.ntnu.idatt2106.sparesti.dto.challenge;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Builder
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
  private String username;
  private String challengeName;
  private String description;
  private LocalDate startDate;
  private LocalDate endDate;
  private String difficulty;
}