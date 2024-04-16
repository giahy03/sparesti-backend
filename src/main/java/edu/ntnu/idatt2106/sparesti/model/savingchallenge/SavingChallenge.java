package edu.ntnu.idatt2106.sparesti.model.savingchallenge;

import edu.ntnu.idatt2106.sparesti.model.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
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
@Builder
@Entity
@RequiredArgsConstructor
public class SavingChallenge implements Challenge {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "challenge_id", nullable = false)
  @Setter(AccessLevel.NONE)
  private Long challengeId;

  @Column(name = "description", nullable = false)
  private String description;

  @Column(name = "save_percentage", nullable = false)
  private int percentage;

  @Column(name = "amount", nullable = false)
  private double amount;

  @Column(name = "start_date", nullable = false)
  private LocalDate startDate;

  @Column(name = "end_date", nullable = false)
  private LocalDate endDate;

  @Column(name = "is_completed", nullable = false)
  private boolean isCompleted;

  @Column(name = "difficulty", nullable = false)
  private Difficulty difficulty;

  @ManyToOne
  @JoinColumn(name="user_id", nullable = false)
  private User user;

  //TODO implement checkCompletion method
  @Override
  public boolean checkCompletion() {
    return false;
  }
}