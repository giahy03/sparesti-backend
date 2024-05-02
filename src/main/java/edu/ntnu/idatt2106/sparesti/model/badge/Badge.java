package edu.ntnu.idatt2106.sparesti.model.badge;

import edu.ntnu.idatt2106.sparesti.model.user.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

/**
 * Badge is an abstract class that represents a stored Badge entity.
 * A Badge belongs to an Achievement type at a particular level.
 *
 * @author Hanne-Sofie Søreide
 */
@Entity
@Table(name = "badges")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Badge {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Schema(description = "The unique identifier for the badge.")
  @Column(name = "badge_id")
  private Long id;

  @ManyToOne
  @NonNull
  @Schema(description = "The user to whom the badge belongs.")
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @ManyToOne
  @NonNull
  @Schema(description = "The achievement the badge is associated with.")
  @JoinColumn(name = "achievement_id", nullable = false)
  private Achievement achievement;

  @Schema(description = "The date at which the badge was earned.")
  @Column(name = "achieved_date", nullable = false)
  @NonNull
  @Temporal(TemporalType.DATE)
  private LocalDate achievedDate;

  @Schema(description = "The level of the achievement that the badge represents.")
  @Column(name = "level", nullable = false)
  private int level;

}
