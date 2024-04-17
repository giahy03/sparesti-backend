package edu.ntnu.idatt2106.sparesti.model.analysis;

import lombok.Getter;

/**
 * Represents the income quartile of a household, based on Ssb standards.
 */
@Getter
public enum SsbIncomeQuartile {
  ALL_INCOME("0"),
  QUARTILE_1("41"),
  QUARTILE_2("42"),
  QUARTILE_3("43"),
  QUARTILE_4("44");

  private final String quartileCode;

  SsbIncomeQuartile(String quartileCode) {
    this.quartileCode = quartileCode;
  }

}