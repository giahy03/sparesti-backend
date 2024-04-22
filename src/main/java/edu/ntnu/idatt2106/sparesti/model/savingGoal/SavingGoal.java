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

    @Column(name="goal_name", nullable = false)
    @NonNull
    private String goalName;

    @Enumerated(EnumType.STRING)
    @Column(name = "difficulty", nullable = false)
    @NonNull
    private GoalDifficulty difficulty;

    @Column(name = "start_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private LocalDate endDate;

    @Column(name = "goal_amount")
    private double amount;

    @Column(name = "goal_progress")
    private double progress;

    @Column(name = "lives")
    private int lives;

    @Column(name = "current_tile")
    private int currentTile;

    // Legge inn LocalDate da mål ble nådd, og om null er den ikke oppnådd?
/*    @Column(name = "achieved")   // Calculate from amount and progress, not needed in db
    private boolean achieved;*/

    public boolean isAchieved() {
        return progress >= amount;
    }
}
