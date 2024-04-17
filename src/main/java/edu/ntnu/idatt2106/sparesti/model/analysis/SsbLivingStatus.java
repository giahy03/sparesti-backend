package edu.ntnu.idatt2106.sparesti.model.analysis;

import lombok.Getter;

/**
 * Represents the living status of a household, based on Ssb standards.
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

}
