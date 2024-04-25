package edu.ntnu.idatt2106.sparesti.model.streak;

import edu.ntnu.idatt2106.sparesti.model.user.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The class represents a streak of consecutive days for a user.
 *
 * @author Ramtin Samavat
 * @version 1.0
 */
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "streaks")
@Schema(description = "A user's streak entity.")
public class Streak {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Schema(description = "The unique identifier for the streak")
  @Column(name = "streak_id")
  @Setter(AccessLevel.NONE)
  private Long streakId;

  @Schema(description = "The number of consecutive days in the streak.")
  @Column(name = "number_of_days", nullable = false)
  private int numberOfDays;

  @OneToOne
  @Schema(description = "The user associated with streak.")
  @JoinColumn(name = "user_id", nullable = false)
  private User user;
}
