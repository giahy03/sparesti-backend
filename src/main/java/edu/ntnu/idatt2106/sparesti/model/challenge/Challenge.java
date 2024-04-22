package edu.ntnu.idatt2106.sparesti.model.challenge;

import edu.ntnu.idatt2106.sparesti.model.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.SuperBuilder;


/**
 * Represents a challenge entity superclass.
 *
 * @author Jeffrey Yaw Annor Tabiri
 * @version 1.0
 */
@NoArgsConstructor
@Entity
@Getter
@Setter
@SuperBuilder
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "challenge")
public class Challenge {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Setter(AccessLevel.NONE)
  private Long id;

  @Column(name = "title", nullable = false)
  @NonNull
  private String title;

  @Column(name = "start_date", nullable = false)
  @NonNull
  private LocalDate startDate;

  @Column(name = "end_date", nullable = false)
  @NonNull
  private LocalDate endDate;

  @Column(name = "is_completed", nullable = false)
  private boolean isCompleted;

  @Enumerated(EnumType.STRING)
  @Column(name = "difficulty", nullable = false)
  @NonNull
  private Difficulty difficulty;

  @Column(name = "lives", nullable = false)
  private int lives;

  @Column(name = "current_tile", nullable = false)
  private int currentTile;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

}
