package edu.ntnu.idatt2106.sparesti.model.savingGoal;

import edu.ntnu.idatt2106.sparesti.model.user.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
    @Column(name = "id")
    @Setter(AccessLevel.NONE)
    private Long id;

    // Owmner og invited. + joincode felt

    @ManyToOne(cascade = CascadeType.ALL)
    @NonNull
    @Schema(description = "The unique identifier for the user who created the goal.")
    @JoinColumn(name="author_id", nullable = false)
    @Setter(AccessLevel.NONE)
    private User author;

    @ManyToMany(cascade = CascadeType.ALL)
    @NonNull
    @Schema(description = "The unique identifier for the user(s) who has this goal.")
    @CollectionTable(name="goal_users", joinColumns=@JoinColumn(name="id"))
    private Set<User> users;

    @Schema(description = "The title of the saving goal.")
    @Column(name="title", nullable = false)
    @NonNull
    private String title;

    @Enumerated(EnumType.STRING)
    @Schema(description = "The state of the saving goal.")
    @Column(name = "state", nullable = false)
    @NonNull
    private GoalState state;

    @Schema(description = "The start date of the saving goal.")
    @Column(name = "start_date", nullable = false)
    @NonNull
    @Temporal(TemporalType.DATE)
    private LocalDate startDate;

    @Schema(description = "The end date of the saving goal.")
    @Column(name = "end_date", nullable = false)
    @NonNull
    @Temporal(TemporalType.DATE)
    private LocalDate endDate;

    @Schema(description = "The amount of money to save up to achieve the saving goal.")
    @Column(name = "goal_amount")
    private double totalAmount;

    @Column(name = "join_code", nullable = false, unique = true)
    @Schema(description = "The join code for a goal")
    @Setter(AccessLevel.NONE)
    private String joinCode = "abc";   // TODO: Temorarily hardcoded.

    @ElementCollection
    @Schema(description = "The amount saved for this goal for each user.")
    @MapKeyColumn(name="user_id")
    @Column(name="amount")
    @CollectionTable(name="user_saving_contribution", joinColumns=@JoinColumn(name="id"))  // goal or user id?
    //@BatchSize(size = 10)
    Map<Long, Double> contributions = new HashMap<>();



    @Schema(description = "The number of lives of the saving mascot at this saving goal.")
    @Column(name = "lives")
    private int lives;

    // TODO: What about failed?
    public boolean isAchieved() {
        if (totalAmount <= getTotalProgress()){
            setState(GoalState.FINISHED);
            return true;
        } else {
            return false;
        }
    }

    public double getTotalProgress() {
        double sum = 0.0;
        for (double value : contributions.values()) {
            sum += value;
        }
        return sum;
    }
}
