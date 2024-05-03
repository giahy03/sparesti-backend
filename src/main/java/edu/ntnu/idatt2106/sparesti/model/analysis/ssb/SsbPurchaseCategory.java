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

  /**
   * Translates the purchase category to Norwegian.
   *
   * @return the purchase category in Norwegian
   */
  public String translateToNorwegian() {
    return switch (this) {
      case IN_TOTAL -> "totalt";
      case FOOD -> "mat";
      case ALCOHOL_AND_TOBACCO -> "alkohol & tobakk";
      case CLOTHING_AND_SHOES -> "klær & sko";
      case HOUSING_AND_ELECTRICITY -> "bolig & strøm";
      case FURNITURE -> "møbler";
      case HEALTH -> "helse";
      case TRANSPORT -> "transport";
      case COMMUNICATION -> "kommunikasjon";
      case LEISURE_SPORT_AND_CULTURE -> "fritid, sport & kultur";
      case EDUCATION -> "utdanning";
      case EATING_OUT -> "spise ute";
      case INSURANCE -> "forsikring";
      case OTHER -> "annet";
    };
  }
}
