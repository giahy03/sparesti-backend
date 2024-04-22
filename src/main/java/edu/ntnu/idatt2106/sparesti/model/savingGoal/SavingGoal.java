package edu.ntnu.idatt2106.sparesti.model.savingGoal;

import edu.ntnu.idatt2106.sparesti.model.user.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

/**
 * Represents a stored saving goal entity.
 *
 * @author Hanne-Sofie Søreide
 */

@Entity
@Table(name = "goals")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SavingGoal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "The unique identifier for the saving goal.")
    @Column(name = "goal_id")
    @Setter(AccessLevel.NONE)
    private Long id;

    @ManyToOne
    @Schema(description = "The unique identifier for the user.")
    @JoinColumn(name="user_id", nullable = false)
    private User user;

    @Schema(description = "The title of the saving goal.")
    @Column(name="goal_name", nullable = false)
    @NonNull
    private String goalName;

    @Enumerated(EnumType.STRING)
    @Schema(description = "The difficulty level of the saving goal.")
    @Column(name = "difficulty", nullable = false)
    @NonNull
    private GoalDifficulty difficulty;

    @Schema(description = "The start date of the saving goal.")
    @Column(name = "start_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private LocalDate startDate;

    @Schema(description = "The end date of the saving goal.")
    @Column(name = "end_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private LocalDate endDate;

    @Schema(description = "The amount of money to save up to achieve the saving goal.")
    @Column(name = "goal_amount")
    private double amount;

    @Schema(description = "The amount of money saved up for this saving goal this far.")
    @Column(name = "goal_progress")
    private double progress;

    @Schema(description = "The number of lives of the saving mascot at this saving goal.")
    @Column(name = "lives")
    private int lives;

    @Schema(description = "The current tile of this saving goal.")
    @Column(name = "current_tile")
    private int currentTile;

    // Legge inn LocalDate da mål ble nådd, og om null er den ikke oppnådd?
/*    @Column(name = "achieved")   // Calculate from amount and progress, not needed in db
    private boolean achieved;*/

    public boolean isAchieved() {
        return progress >= amount;
    }
}
