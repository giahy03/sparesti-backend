package edu.ntnu.idatt2106.sparesti.model.badge;

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
    private Long achievement_id;

    @OneToMany(mappedBy="achievement")
    private Set<Badge> badges;

    @Column(name = "category", nullable = false)
    @NonNull
    private AchievementCategory category;

    @Column(name = "no_of_levels", nullable = false)
    private int noOfLevels;

    @Column(name = "description", nullable = false)
    @NonNull
    private String description;

    @ElementCollection
    @CollectionTable(
        name = "thresholds",
        joinColumns = @JoinColumn(name = "achievement_id")
    )
    @Column( name = "threshold")
    private List<Integer> thresholds;

}
