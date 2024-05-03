package edu.ntnu.idatt2106.sparesti.model.analysis.ssb;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class SsbPurchaseCategoryTest {

  @Test
  void testCategoryCodes() {
    assertEquals("00", SsbPurchaseCategory.IN_TOTAL.getCategoryCode());
    assertEquals("01", SsbPurchaseCategory.FOOD.getCategoryCode());
    assertEquals("02", SsbPurchaseCategory.ALCOHOL_AND_TOBACCO.getCategoryCode());
    assertEquals("03", SsbPurchaseCategory.CLOTHING_AND_SHOES.getCategoryCode());
    assertEquals("04", SsbPurchaseCategory.HOUSING_AND_ELECTRICITY.getCategoryCode());
    assertEquals("05", SsbPurchaseCategory.FURNITURE.getCategoryCode());
    assertEquals("06", SsbPurchaseCategory.HEALTH.getCategoryCode());
    assertEquals("07", SsbPurchaseCategory.TRANSPORT.getCategoryCode());
    assertEquals("08", SsbPurchaseCategory.COMMUNICATION.getCategoryCode());
    assertEquals("09", SsbPurchaseCategory.LEISURE_SPORT_AND_CULTURE.getCategoryCode());
    assertEquals("10", SsbPurchaseCategory.EDUCATION.getCategoryCode());
    assertEquals("11", SsbPurchaseCategory.EATING_OUT.getCategoryCode());
    assertEquals("12", SsbPurchaseCategory.INSURANCE.getCategoryCode());
    assertEquals("13", SsbPurchaseCategory.OTHER.getCategoryCode());
  }

}
