package edu.ntnu.idatt2106.sparesti.model.savingchallenge;

import edu.ntnu.idatt2106.sparesti.model.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import java.time.LocalDate;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "challenge")
@Inheritance(strategy = InheritanceType.JOINED)
@SuperBuilder
public abstract class Challenge {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "description", nullable = false)
  private String description;

  @Column(name = "start_date", nullable = false)
  private LocalDate startDate;

  @Column(name = "end_date", nullable = false)
  private LocalDate endDate;

  @Column(name = "is_completed", nullable = false)
  private boolean isCompleted;

  @Column(name = "difficulty", nullable = false)
  private Difficulty difficulty;

  @ManyToOne
  @JoinColumn(name="user_id", nullable = false)
  private User user;
}
