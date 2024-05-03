package edu.ntnu.idatt2106.sparesti.dto.challenge;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;

/**
 * Test class for the ChallengeRecommendationDto.
 *
 * @author Tobias Oftedal
 */
class ChallengeRecommendationDtoTest {

  @Test
  void givenValidData_settersAndGetters_work() {
    //arrange
    String goalDescription = "goalDescription";
    LocalDate startDate = LocalDate.now();
    LocalDate endDate = LocalDate.now().plusDays(1);
    Double dailyAmount = 1.0;
    String category = "category";
    ChallengeRecommendationDto challengeRecommendationDto = new ChallengeRecommendationDto(
        "", LocalDate.MIN, LocalDate.MAX, 0.0, "");


    //act
    challengeRecommendationDto.setGoalDescription(goalDescription);
    challengeRecommendationDto.setStartDate(startDate);
    challengeRecommendationDto.setEndDate(endDate);
    challengeRecommendationDto.setDailyAmount(dailyAmount);
    challengeRecommendationDto.setCategory(category);

    //assert
    assertEquals(goalDescription, challengeRecommendationDto.getGoalDescription());
    assertEquals(startDate, challengeRecommendationDto.getStartDate());
    assertEquals(endDate, challengeRecommendationDto.getEndDate());
    assertEquals(dailyAmount, challengeRecommendationDto.getDailyAmount());
    assertEquals(category, challengeRecommendationDto.getCategory());
  }
}
