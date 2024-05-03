package edu.ntnu.idatt2106.sparesti.model.badge;

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
import lombok.NonNull;
import lombok.Setter;

/**
 * The class keeps track of the relevant statistics for possible badges for the user.
 *
 * @author Hanne-Sofie Søreide
 */

@Entity
@Table(name = "achievement_stats")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AchievementStats {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Schema(description = "The unique identifier for the set of achievement stats")
  @Column(name = "stats_id")
  @Setter(AccessLevel.NONE)
  private Long id;

  @OneToOne
  @NonNull
  @Schema(description = "The user to whom the stats belong")
  @JoinColumn(name = "user", nullable = false)
  private User user;

  @Schema(description = "The longest streak that the user has achieved in Sparesti")
  @Column(name = "streak")
  private int streak;

  @Schema(description = "The total number of accomplished challenges of the user")
  @Column(name = "challenges_achieved")
  private int challengesAchieved;

  @Schema(description = "Number of saving goals the user has completed in Sparesti")
  @Column(name = "saving_goals_achieved")
  private int savingGoalsAchieved;

  @Schema(description = "The total amount saved by the user in Sparesti")
  @Column(name = "total_saved")
  private double totalSaved;

  @Schema(description = "If the user has clicked on any of the news links in Sparesti ")
  @Column(name = "read_news")
  private boolean readNews;
}
