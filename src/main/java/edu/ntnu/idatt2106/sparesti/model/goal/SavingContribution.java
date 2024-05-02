package edu.ntnu.idatt2106.sparesti.model.goal;

import edu.ntnu.idatt2106.sparesti.model.user.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entity that connects Saving goal and User objects. The entity keeps track of
 * which goals users are contributing to and their current contribution to each goal.
 *
 * @author Hanne-Sofie Søreide
 */

@Entity
@Table(name = "goal_contributions")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SavingContribution {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Schema(description = "The unique identifier for the saving goal.")
  @Setter(AccessLevel.NONE)
  private Long id;

  @Schema(description = "The user who made the contribution.")
  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
  private User user;

  @Schema(description = "The saving goal the contribution was made to.")
  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
  private SavingGoal goal;

  @Schema(description = "The amount contributed by the user to achieve the saving goal.")
  private double contribution;

}
