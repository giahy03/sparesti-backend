package edu.ntnu.idatt2106.sparesti.model.analysis.ssb;

import lombok.Getter;

/**
 * Represents the living status of a household, based on Ssb standards.
 *
 * @author Tobias Oftedal
 * @version 1.0
 */
@Getter
public enum SsbLivingStatus {

  ALL_KINDS_OF_HOUSEHOLDS("0"),

  LIVING_ALONE("1"),

  COUPLE_WITHOUT_CHILDREN("2"),

  COUPLE_WITH_CHILDREN("3-4");

  private final String status;

  SsbLivingStatus(String status) {
    this.status = status;
  }

  /**
   * Maps an integer to the corresponding SsbLivingStatus enum value.
   *
   * @param value The integer value representing the living status.
   * @return The SsbLivingStatus enum value corresponding to the given integer.
   * @throws IllegalArgumentException if the integer does not map to any enum value.
   */
  public static SsbLivingStatus fromInteger(int value) {
    return switch (value) {
      case 0 -> ALL_KINDS_OF_HOUSEHOLDS;
      case 1 -> LIVING_ALONE;
      case 2 -> COUPLE_WITHOUT_CHILDREN;
      case 3, 4 -> COUPLE_WITH_CHILDREN;
      default -> throw new IllegalArgumentException(
          "Invalid integer value for SsbLivingStatus: " + value
      );
    };
  }
}
