package edu.ntnu.idatt2106.sparesti.model.analysis.ssb;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class SsbIncomQuartileTest {

  @Test
  void givenFirstQuartileIncome_whenCalculateFromDouble_thenReturnCorrectSsbIncomeQuartile() {
    //Arrange
    double monthlyIncomeAfterTaxes = 29999;

    //Act
    SsbIncomeQuartile result = SsbIncomeQuartile.from(monthlyIncomeAfterTaxes);

    //Assert
    assertEquals(SsbIncomeQuartile.QUARTILE_1, result);
  }

  @Test
  void givenSecondQuartileIncome_whenCalculateFromDouble_thenReturnCorrectSsbIncomeQuartile() {
    //Arrange
    double monthlyIncomeAfterTaxes = 39999;

    //Act
    SsbIncomeQuartile result = SsbIncomeQuartile.from(monthlyIncomeAfterTaxes);

    //Assert
    assertEquals(SsbIncomeQuartile.QUARTILE_2, result);
  }

  @Test
  void givenThirdQuartileIncome_whenCalculateFromDouble_thenReturnCorrectSsbIncomeQuartile() {
    //Arrange
    double monthlyIncomeAfterTaxes = 49999;

    //Act
    SsbIncomeQuartile result = SsbIncomeQuartile.from(monthlyIncomeAfterTaxes);

    //Assert
    assertEquals(SsbIncomeQuartile.QUARTILE_3, result);
  }

  @Test
  void givenFourthQuartileIncome_whenCalculateFromDouble_thenReturnCorrectSsbIncomeQuartile() {
    //Arrange
    double monthlyIncomeAfterTaxes = 50000;

    //Act
    SsbIncomeQuartile result = SsbIncomeQuartile.from(monthlyIncomeAfterTaxes);

    //Assert
    assertEquals(SsbIncomeQuartile.QUARTILE_4, result);
  }

  @Test
  void testQuartileCodes() {
    assertEquals("0", SsbIncomeQuartile.ALL_INCOME.getQuartileCode());
    assertEquals("41", SsbIncomeQuartile.QUARTILE_1.getQuartileCode());
    assertEquals("42", SsbIncomeQuartile.QUARTILE_2.getQuartileCode());
    assertEquals("43", SsbIncomeQuartile.QUARTILE_3.getQuartileCode());
    assertEquals("44", SsbIncomeQuartile.QUARTILE_4.getQuartileCode());
  }

}
