package edu.ntnu.idatt2106.sparesti.model.challenge;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * Represents a shared challenge entity.
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

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "shared_id")
  private SharedChallengeCode sharedChallengeCode;

}