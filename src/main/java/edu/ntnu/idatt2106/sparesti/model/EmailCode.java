package edu.ntnu.idatt2106.sparesti.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "email_code")
public class EmailCode {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "email_id")
  @Setter(AccessLevel.NONE)
  private Long id;

  @Column(name = "email", nullable = false, unique = true)
  @NonNull
  private String email;

  @Column(name = "verification_code", nullable = false)
  @NonNull
  private String verificationCode;
}