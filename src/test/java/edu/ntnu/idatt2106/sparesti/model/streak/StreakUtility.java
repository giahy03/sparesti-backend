package edu.ntnu.idatt2106.sparesti.model.streak;

import edu.ntnu.idatt2106.sparesti.dto.streak.StreakRequestDto;
import edu.ntnu.idatt2106.sparesti.model.streak.Streak;

public class StreakUtility {

  public static StreakRequestDto createStreakRequestDto1() {
    StreakRequestDto streakRequestDto = new StreakRequestDto();
    streakRequestDto.setIncrement(true);
    return streakRequestDto;
  }

  public static StreakRequestDto createStreakRequestDto2() {
    StreakRequestDto streakRequestDto = new StreakRequestDto();
    streakRequestDto.setIncrement(false);
    return streakRequestDto;
  }

  public static Streak createStreak1() {
    return Streak.builder()
            .numberOfDays(20)
            .build();
  }

  public static String createStreakRequestDtoJson() {
        return "{\"increment\": true}";
  }


}
