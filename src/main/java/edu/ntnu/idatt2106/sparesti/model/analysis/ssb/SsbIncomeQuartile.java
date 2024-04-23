package edu.ntnu.idatt2106.sparesti.model.analysis.ssb;

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

  /**
   * Returns the income quartile of a household, based on the monthly income after taxes.
   *
   * @param monthlyIncomeAfterTaxes The monthly income after taxes.
   * @return The income quartile of the household.
   */
  public static SsbIncomeQuartile from(double monthlyIncomeAfterTaxes) {
    if (monthlyIncomeAfterTaxes < 30000) {
      return QUARTILE_1;
    } else if (monthlyIncomeAfterTaxes < 40000) {
      return QUARTILE_2;
    } else if (monthlyIncomeAfterTaxes < 50000) {
      return QUARTILE_3;
    } else {
      return QUARTILE_4;
    }
  }

  SsbIncomeQuartile(String quartileCode) {
    this.quartileCode = quartileCode;
  }

}