package edu.ntnu.idatt2106.sparesti.model.challenge;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * Represents a saving-challenge entity.
 *
 * @author Jeffrey Yaw Annor Tabiri
 * @version 1.0
 */
@Entity
@Table(name = "shared_challenge")
@SuperBuilder
@NoArgsConstructor
@Getter
@Setter
public class SharedChallenge extends Challenge {

  @ManyToOne
  @JoinColumn(name = "id", nullable = false)
  private SharedChallengeCode sharedChallengeCode;

}