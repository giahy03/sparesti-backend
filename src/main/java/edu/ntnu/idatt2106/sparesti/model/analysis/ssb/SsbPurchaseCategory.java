package edu.ntnu.idatt2106.sparesti.model.analysis.ssb;

import lombok.Getter;

/**
 * Represents the living status of a household. Based on Ssb standards.
 */
@Getter
public enum SsbPurchaseCategory {
  IN_TOTAL("00"),
  FOOD("01"),
  ALCOHOL_AND_TOBACCO("02"),
  CLOTHING_AND_SHOES("03"),
  HOUSING_AND_ELECTRICITY("04"),
  FURNITURE("05"),
  HEALTH("06"),
  TRANSPORT("07"),
  COMMUNICATION("08"),
  LEISURE_SPORT_AND_CULTURE("09"),
  EDUCATION("10"),
  EATING_OUT("11"),
  INSURANCE("12"),
  OTHER("13");


  final String categoryCode;

  SsbPurchaseCategory(String categoryCode) {
    this.categoryCode = categoryCode;
  }
}
