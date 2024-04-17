package edu.ntnu.idatt2106.sparesti.model.badge;

import edu.ntnu.idatt2106.sparesti.model.user.User;
import jakarta.persistence.*;
import lombok.experimental.SuperBuilder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import java.util.Date;

/**
 * Badge is an abstract class that represents a stored Badge entity.
 * A Badge belongs to an Achievement type at a particular level.
 *
 * @author Hanne-Sofie Søreide
 */
@Entity
@Table(name = "badges")  // lagres kanskje heller som en userstat tabell?
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public abstract class Badge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "badge_id")
    private Long badge_id;

    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "achievement_id", nullable = false)
    private Achievement achievement;

    @Column(name = "achieved", nullable = false)
    @NonNull
    @Temporal(TemporalType.DATE)
    private Date achieved;

    @Column(name = "level", nullable = false)
    @NonNull
    private int level;

}
