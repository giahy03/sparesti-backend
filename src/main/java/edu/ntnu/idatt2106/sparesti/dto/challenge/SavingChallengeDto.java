package edu.ntnu.idatt2106.sparesti.dto.challenge;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@JsonTypeName("SavingChallengeDto")
public class SavingChallengeDto extends ChallengeDto{
    private double totalAmount;
    private double currentAmount;
}