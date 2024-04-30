package edu.ntnu.idatt2106.sparesti.model.savingGoal;


import com.fasterxml.jackson.annotation.JsonIgnore;
import edu.ntnu.idatt2106.sparesti.model.user.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

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
    @Column(name = "id")
    @Setter(AccessLevel.NONE)
    private Long id;

    @ManyToOne//(cascade = CascadeType.ALL)
    @NonNull
    @Schema(description = "The unique identifier for the user.")
    @JoinColumn(name="user_id", nullable = false)
    @Setter(AccessLevel.NONE)
    private User user;

    @ManyToOne//(cascade = CascadeType.ALL)
    @NonNull
    @Schema(description = "The unique identifier for the goal.")
    @JoinColumn(name="goal_id", nullable = false)
    @Setter(AccessLevel.NONE)
    private SavingGoal goal;

    @Schema(description = "The amount contributed by the user to achieve the saving goal.")
    @Column(name = "contribution")
    private double contribution;


}
