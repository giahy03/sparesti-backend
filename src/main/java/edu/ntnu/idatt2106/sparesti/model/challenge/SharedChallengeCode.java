package edu.ntnu.idatt2106.sparesti.model.challenge;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Entity
@Table(name = "shared_challenge_code")
@NoArgsConstructor
@Getter
@Setter
public class SharedChallengeCode {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  @Getter(AccessLevel.NONE)
  private Long id;

  @Column(name = "verification_code", nullable = false)
  @Schema(description = "The verification code.")
  @Setter(AccessLevel.NONE)
  @NonNull
  private String joinCode;

}
