package edu.ntnu.idatt2106.sparesti.model.badge;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import java.util.List;
import java.util.Set;


/**
 * The Achievement class represents different types of achievements that a
 * user can accomplish. Each different achievement is divided in a number of
 * levels that each is associated with a badge.
 *
 * @author Hanne-Sofie Søreide
 */


@Entity
@Table(name = "achievements")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Achievement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "The unique identifier of the achievement.")
    @Column(name = "achievement_id")
    private int id;

    @Enumerated(EnumType.STRING)
    @Schema(description = "The category that the achievement belongs to.")
    @Column(name = "category", nullable = false)
    @NonNull
    private AchievementCategory category;

    @Schema(description = "Description of the achievement.")
    @Column(name = "description", nullable = false)
    @NonNull
    private String description;

    @ElementCollection
    @NonNull
    @Schema(description = "The threshold of the achievable levels of the achievement.")
    @CollectionTable(
            name = "thresholds",
            joinColumns = @JoinColumn(name = "achievement_id")
    )
    @Column( name = "threshold")
    private List<Integer> thresholds;


}
