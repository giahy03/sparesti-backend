package edu.ntnu.idatt2106.sparesti.model.savingGoal;

import edu.ntnu.idatt2106.sparesti.model.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

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
    @Column(name = "goal_id")
    @Setter(AccessLevel.NONE)
    private Long goalId;

    @ManyToOne
    @JoinColumn(name="user", nullable = false)
    @NonNull
    private User user;

    @Column(name="goal_name", nullable = false)
    @NonNull
    private String goalName;

    @Column(name = "deadline", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date deadline;

    @Column(name = "goal_amount")
    private double amount;

    @Column(name = "goal_progress")
    private double progress;

    @Column(name = "achieved")
    private boolean achieved;

}
