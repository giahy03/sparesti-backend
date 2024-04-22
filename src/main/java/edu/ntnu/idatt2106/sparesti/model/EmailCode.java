package edu.ntnu.idatt2106.sparesti.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

/**
 * The class represents an email code entity used for email verification.
 *
 * @author Ramtin Samavat
 * @author Jeff Tabiri
 * @version 1.0
 */
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "email_code")
@Schema(description = "An email code entity.")
public class EmailCode {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  @Schema(description = "The unique identifier for the email code.")
  @Setter(AccessLevel.NONE)
  @Getter(AccessLevel.NONE)
  private Long id;

  @Column(name = "email", nullable = false, unique = true)
  @Schema(description = "The email associated with the code.")
  @Setter(AccessLevel.NONE)
  @NonNull
  private String email;

  @Column(name = "verification_code", nullable = false)
  @Schema(description = "The verification code.")
  @Setter(AccessLevel.NONE)
  @NonNull
  private String verificationCode;

  @Column(name = "expiry_timestamp", nullable = false)
  @Schema(description = "The expiry timestamp of the code.")
  @Setter(AccessLevel.NONE)
  @NonNull
  private LocalDate expiryTimestamp;
}