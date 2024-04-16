package edu.ntnu.idatt2106.sparesti.model.savingchallenge;

import edu.ntnu.idatt2106.sparesti.model.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

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
}