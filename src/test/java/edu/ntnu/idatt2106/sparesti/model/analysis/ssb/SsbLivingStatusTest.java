package edu.ntnu.idatt2106.sparesti.model.analysis.ssb;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;


class SsbLivingStatusTest {

  @Test
  void testCodes() {
    assertEquals("0", SsbLivingStatus.ALL_KINDS_OF_HOUSEHOLDS.getStatus());
    assertEquals("1", SsbLivingStatus.LIVING_ALONE.getStatus());
    assertEquals("2", SsbLivingStatus.COUPLE_WITHOUT_CHILDREN.getStatus());
    assertEquals("3-4", SsbLivingStatus.COUPLE_WITH_CHILDREN.getStatus());
  }

  @Test
  void givenStatusValue0_whenCalculateFromInteger_thenReturnCorrectSsbLivingStatus() {
    //Arrange
    int statusValue = 0;

    //Act
    SsbLivingStatus result = SsbLivingStatus.fromInteger(statusValue);

    //Assert
    assertEquals(SsbLivingStatus.ALL_KINDS_OF_HOUSEHOLDS, result);
  }

  @Test
  void givenStatusValue1_whenCalculateFromInteger_thenReturnCorrectSsbLivingStatus() {
    //Arrange
    int statusValue = 1;

    //Act

    SsbLivingStatus result = SsbLivingStatus.fromInteger(statusValue);

    //Assert

    assertEquals(SsbLivingStatus.LIVING_ALONE, result);
  }

  @Test
  void givenStatusValue2_whenCalculateFromInteger_thenReturnCorrectSsbLivingStatus() {
    //Arrange
    int statusValue = 2;

    //Act

    SsbLivingStatus result = SsbLivingStatus.fromInteger(statusValue);

    //Assert

    assertEquals(SsbLivingStatus.COUPLE_WITHOUT_CHILDREN, result);
  }

  @Test
  void givenStatusValue3_whenCalculateFromInteger_thenReturnCorrectSsbLivingStatus() {
    //Arrange
    int statusValue = 3;

    //Act

    SsbLivingStatus result = SsbLivingStatus.fromInteger(statusValue);

    //Assert

    assertEquals(SsbLivingStatus.COUPLE_WITH_CHILDREN, result);
  }

  @Test
  void givenStatusValue4_whenCalculateFromInteger_thenReturnCorrectSsbLivingStatus() {
    //Arrange
    int statusValue = 4;

    //Act

    SsbLivingStatus result = SsbLivingStatus.fromInteger(statusValue);

    //Assert

    assertEquals(SsbLivingStatus.COUPLE_WITH_CHILDREN, result);
  }

  @Test
  void givenInvalidStatusValue_whenCalculateFromInteger_thenThrowIllegalArgumentException() {
    //Arrange
    int statusValue = 5;

    //Act & Assert
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> SsbLivingStatus.fromInteger(statusValue));
    assertEquals("Invalid integer value for SsbLivingStatus: " + statusValue, exception.getMessage());
  }
}
