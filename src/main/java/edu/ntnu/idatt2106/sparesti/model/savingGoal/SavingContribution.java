package edu.ntnu.idatt2106.sparesti.model.savingGoal;

import edu.ntnu.idatt2106.sparesti.model.user.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

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
    @ManyToOne (fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private User user;

    @Schema(description = "The saving goal the contribution was made to.")
    @ManyToOne (fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private SavingGoal goal;

    @Schema(description = "The amount contributed by the user to achieve the saving goal.")
    private double contribution;

}
