package edu.ntnu.idatt2106.sparesti.model.savingGoal;

import edu.ntnu.idatt2106.sparesti.model.user.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.HashMap;
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


    @ManyToOne(cascade = CascadeType.ALL)
    @NonNull
    @Schema(description = "The unique identifier for the user who created the goal.")
    @JoinColumn(name="author_id", nullable = false)
    @Setter(AccessLevel.NONE)
    private User author;

/*
    @OneToMany(mappedBy = "goal")//(cascade = CascadeType.ALL, mappedBy = "goal")
    @Schema(description = "The contributions made by the user(s) associated with this goal.")
    //@JoinColumn(name="goal", nullable = false)
    private Set<SavingContribution> savingContribution;
*/


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

    // Kobling til SavingContribution. -> Liste med brukere som bidrar og beløp fra hver.


/*
    @ElementCollection
    @Schema(description = "The amount saved for this goal for each user.")
    @MapKeyColumn(name="user_id")
    @Column(name="amount", nullable = false)
    @CollectionTable(name="user_saving_contribution", joinColumns=@JoinColumn(name="id"))  // goal or user id?
    //@BatchSize(size = 10)
    Map<Long, Double> contributions = new HashMap<>();
*/



    @Schema(description = "The number of lives of the saving mascot at this saving goal.")
    @Column(name = "lives")
    private int lives;

/*
    *//**
     * Check if the saving goal has been achieved before the end date of the goal and set the goal state accordingly.
     * @return True if the goal was achieved before the end date, false if not.
     *//*
    public boolean isAchieved() {
        if (totalAmount <= getTotalProgress()){
            setState(GoalState.FINISHED);
            return true;
        } else if (totalAmount >= getTotalProgress() && getEndDate().isAfter(LocalDate.now())) {
            setState(GoalState.FAILED);
            return false;
        } else {
            setState(GoalState.UNDER_PROGRESS);
            return false;
        }
    }

    *//**
     * Calculates the total saved amount from all contributing users of this goal.
     * @return The currently saved up amount by all users.
     *//*
    public double getTotalProgress() {
        double sum = 0.0;
        for (double value : contributions.values()) {
            sum += value;
        }
        return sum;
    }*/

}
