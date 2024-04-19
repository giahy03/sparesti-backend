package edu.ntnu.idatt2106.sparesti.model.user;

import edu.ntnu.idatt2106.sparesti.model.analysis.ssb.SsbLivingStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The class represents additional information about a user.
 * This includes their income and living status.
 *
 * @author Ramtin Samavat
 * @version 1.0
 */
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_info")
@Schema(description = "Additional information about a user.")
public class UserInfo {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Schema(description = "The unique identifier for the user's information.")
  @Column(name = "id")
  @Setter(AccessLevel.NONE)
  private Long id;

  @Schema(description = "The user's income.")
  @Column(name = "income")
  private double income;

  @Enumerated(EnumType.ORDINAL)
  @Schema(description = "The user's living status.")
  @Column(name = "living_status", nullable = false)
  private SsbLivingStatus livingStatus;

  @OneToOne
  @Schema(description = "The user associated with this additional information.")
  @JoinColumn(name = "user_id", nullable = false)
  private User user;
}
