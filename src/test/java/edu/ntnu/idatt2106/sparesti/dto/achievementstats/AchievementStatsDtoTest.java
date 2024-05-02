package edu.ntnu.idatt2106.sparesti.dto.achievementstats;

import static org.junit.jupiter.api.Assertions.assertEquals;

import edu.ntnu.idatt2106.sparesti.dto.achievementStats.AchievementStatsDto;
import org.junit.jupiter.api.Test;


class AchievementStatsDtoTest {

  @Test
  void givenValidInput_gettersAndSetters_shouldReturnCorrectValues() {
    //arrange
    int expectedId = 1;
    int expectedStreak = 2;
    int expectedChallengesAchieved = 3;
    int expectedSavingGoalsAchieved = 4;
    double expectedTotalSaved = 5.0;
    int expectedChallengeStreak = 6;
    boolean expectedReadNews = true;


    AchievementStatsDto achievementStatsDto = AchievementStatsDto.builder()
        .id(0)
        .streak(0)
        .challengeStreak(0)
        .challengesAchieved(0)
        .savingGoalsAchieved(0)
        .totalSaved(0)
        .readNews(false)
        .challengeStreak(0)
        .build();

    //act
    achievementStatsDto.setId(expectedId);
    achievementStatsDto.setStreak(expectedStreak);
    achievementStatsDto.setChallengesAchieved(expectedChallengesAchieved);
    achievementStatsDto.setSavingGoalsAchieved(expectedSavingGoalsAchieved);
    achievementStatsDto.setTotalSaved(expectedTotalSaved);
    achievementStatsDto.setReadNews(expectedReadNews);
    achievementStatsDto.setChallengeStreak(expectedChallengeStreak);


    assertEquals(expectedId, achievementStatsDto.getId());
    assertEquals(expectedStreak, achievementStatsDto.getStreak());
    assertEquals(expectedChallengesAchieved, achievementStatsDto.getChallengesAchieved());
    assertEquals(expectedSavingGoalsAchieved, achievementStatsDto.getSavingGoalsAchieved());
    assertEquals(expectedTotalSaved, achievementStatsDto.getTotalSaved());
    assertEquals(expectedReadNews, achievementStatsDto.isReadNews());
    assertEquals(expectedChallengeStreak, achievementStatsDto.getChallengeStreak());
  }
}
