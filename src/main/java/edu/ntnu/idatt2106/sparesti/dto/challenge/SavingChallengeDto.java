package edu.ntnu.idatt2106.sparesti.dto.challenge;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;


/**
 * Represents a saving challenge dto.
 * The saving challenge dto contains the total amount and current amount of the challenge.
 *
 * @version 1.0
 * @author Jeffrey Yaw Annor Tabiri
 */
@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@JsonTypeName("SavingChallengeDto")
public class SavingChallengeDto extends ChallengeDto {
    private double totalAmount;
    private double currentAmount;
}