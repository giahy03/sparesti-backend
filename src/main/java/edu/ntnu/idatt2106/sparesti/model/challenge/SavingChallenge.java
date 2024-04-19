package edu.ntnu.idatt2106.sparesti.model.challenge;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * Represents a saving-challenge entity.
 *
 * @author Jeffrey Yaw Annor Tabiri
 * @version 1.0
 */
@Entity
@Table(name = "saving_challenge")
@SuperBuilder
@NoArgsConstructor
@Getter
@Setter
public class SavingChallenge extends Challenge {

  @Column(name = "amount", nullable = false)
  private double totalAmount;

  @Column(name = "current_amount", nullable = false)
  private double currentAmount;

}