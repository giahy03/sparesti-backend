package edu.ntnu.idatt2106.sparesti.model.streak;

import edu.ntnu.idatt2106.sparesti.dto.streak.StreakRequestDto;

/**
 * Utility class that creates objects to support the testing classes.
 *
 * @author Hanne-Sofie Søreide
 */
public class StreakUtility {

  /**
   * Creates a streak object.
   *
   * @return a streak object.
   */
  public static StreakRequestDto createStreakRequestDto1() {
    StreakRequestDto streakRequestDto = new StreakRequestDto();
    streakRequestDto.setIncrement(true);
    return streakRequestDto;
  }

  /**
   * Creates a streak object.
   *
   * @return a streak object.
   */
  public static StreakRequestDto createStreakRequestDto2() {
    StreakRequestDto streakRequestDto = new StreakRequestDto();
    streakRequestDto.setIncrement(false);
    return streakRequestDto;
  }

  /**
   * Creates a json string of a streak request dto.
   *
   * @return a json string of a streak request dto.
   */
  public static String createStreakRequestDtoJson() {
    return "{\"increment\": true}";
  }
}
