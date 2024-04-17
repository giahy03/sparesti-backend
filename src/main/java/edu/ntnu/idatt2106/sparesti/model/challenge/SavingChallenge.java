package edu.ntnu.idatt2106.sparesti.model.challenge;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents a stores saving challenge entity.
 *
 * @author Jeffrey Yaw Annor Tabiri
 * @version 1.0
 */
@Getter
@Setter
@Table(name = "saving_challenge")
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class SavingChallenge extends Challenge {

  @Column(name = "amount", nullable = false)
  private double totalAmount;

  @Column(name = "current_amount", nullable = false)
  private double currentAmount;

  @Override
  public boolean isCompleted() {
    return currentAmount >= totalAmount;
  }
}