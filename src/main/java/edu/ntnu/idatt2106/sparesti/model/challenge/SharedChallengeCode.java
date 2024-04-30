package edu.ntnu.idatt2106.sparesti.model.challenge;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


/**
 * Represents a shared challenge code.
 *
 * @version 1.0
 * @author Jeffrey Yaw Annor Tabiri
 */
@Entity
@Table(name = "shared_challenge_code")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SharedChallengeCode {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "shared_id")
  @Setter(AccessLevel.NONE)
  private Long id;

  @Column(name = "join_code", nullable = false, unique = true)
  @Schema(description = "The join code")
  @Setter(AccessLevel.NONE)
  private String joinCode;

  @OneToMany(mappedBy = "sharedChallengeCode")
  private List<SharedChallenge> sharedChallenges;
}
