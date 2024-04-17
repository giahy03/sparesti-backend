package edu.ntnu.idatt2106.sparesti.dto.saving;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Getter
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "challengeType")
@JsonSubTypes({
        @JsonSubTypes.Type(value = SavingGoalDto.class, name = "SavingGoalDto")}
)

public class SavingGoalDto {
    private String username;
    private String goalName;

    private String description;
    private LocalDate deadLine;
    private double amount;
    private double progress;
    private boolean achieved;
    
}
