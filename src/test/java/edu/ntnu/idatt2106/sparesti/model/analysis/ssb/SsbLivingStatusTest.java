package edu.ntnu.idatt2106.sparesti.model.analysis.ssb;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Test class for the SsbLivingStatus enum.
 *
 * @author Tobias Oftedal
 */
class SsbLivingStatusTest {

  @Test
  void testCodes() {
    assertEquals("0", SsbLivingStatus.ALL_KINDS_OF_HOUSEHOLDS.getStatus());
    assertEquals("1", SsbLivingStatus.LIVING_ALONE.getStatus());
    assertEquals("2", SsbLivingStatus.COUPLE_WITHOUT_CHILDREN.getStatus());
    assertEquals("3-4", SsbLivingStatus.COUPLE_WITH_CHILDREN.getStatus());
  }

  @Test
  @DisplayName("Test that status value 0 returns correct SsbLivingStatus")
  void givenStatusValue0_whenCalculateFromInteger_thenReturnCorrectSsbLivingStatus() {
    //Arrange
    int statusValue = 0;

    //Act
    SsbLivingStatus result = SsbLivingStatus.fromInteger(statusValue);

    //Assert
    assertEquals(SsbLivingStatus.ALL_KINDS_OF_HOUSEHOLDS, result);
  }

  @Test
  @DisplayName("Test that status value 1 returns correct SsbLivingStatus")
  void givenStatusValue1_whenCalculateFromInteger_thenReturnCorrectSsbLivingStatus() {
    //Arrange
    int statusValue = 1;

    //Act
    SsbLivingStatus result = SsbLivingStatus.fromInteger(statusValue);

    //Assert
    assertEquals(SsbLivingStatus.LIVING_ALONE, result);
  }

  @Test
  @DisplayName("Test that status value 2 returns correct SsbLivingStatus")
  void givenStatusValue2_whenCalculateFromInteger_thenReturnCorrectSsbLivingStatus() {
    //Arrange
    int statusValue = 2;

    //Act
    SsbLivingStatus result = SsbLivingStatus.fromInteger(statusValue);

    //Assert
    assertEquals(SsbLivingStatus.COUPLE_WITHOUT_CHILDREN, result);
  }

  @Test
  @DisplayName("Test that status value 3 returns correct SsbLivingStatus")
  void givenStatusValue3_whenCalculateFromInteger_thenReturnCorrectSsbLivingStatus() {
    //Arrange
    int statusValue = 3;

    //Act

    SsbLivingStatus result = SsbLivingStatus.fromInteger(statusValue);

    //Assert
    assertEquals(SsbLivingStatus.COUPLE_WITH_CHILDREN, result);
  }

  @DisplayName("Test that status value 4 returns correct SsbLivingStatus")
  @Test
  void givenStatusValue4_whenCalculateFromInteger_thenReturnCorrectSsbLivingStatus() {
    //Arrange
    int statusValue = 4;

    //Act
    SsbLivingStatus result = SsbLivingStatus.fromInteger(statusValue);

    //Assert
    assertEquals(SsbLivingStatus.COUPLE_WITH_CHILDREN, result);
  }

  @DisplayName("Test that invalid integer value throws IllegalArgumentException")
  @Test
  void givenInvalidStatusValue_whenCalculateFromInteger_thenThrowIllegalArgumentException() {
    //Arrange
    int statusValue = 5;

    //Act & Assert
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
            SsbLivingStatus.fromInteger(statusValue));
    assertEquals("Invalid integer value for SsbLivingStatus: "
            + statusValue, exception.getMessage());
  }
}
