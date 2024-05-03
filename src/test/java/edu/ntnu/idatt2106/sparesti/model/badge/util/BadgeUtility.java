package edu.ntnu.idatt2106.sparesti.model.badge.util;


import edu.ntnu.idatt2106.sparesti.dto.achievementstats.AchievementPreviewDto;
import edu.ntnu.idatt2106.sparesti.dto.badge.BadgeCreateDto;
import edu.ntnu.idatt2106.sparesti.dto.badge.BadgeIdDto;
import edu.ntnu.idatt2106.sparesti.dto.badge.BadgePreviewDto;
import edu.ntnu.idatt2106.sparesti.model.badge.Achievement;
import edu.ntnu.idatt2106.sparesti.model.badge.AchievementCategory;
import edu.ntnu.idatt2106.sparesti.model.badge.Badge;
import edu.ntnu.idatt2106.sparesti.model.user.Role;
import edu.ntnu.idatt2106.sparesti.model.user.User;
import java.time.LocalDate;
import java.util.List;

/**
 * Utility class that creates objects to support the testing related to badges.
 *
 * @author Hanne-Sofie Søreide
 */
public class BadgeUtility {


  /**
   * Creates a user object for testing.
   *
   * @return a user object
   */
  public static User createUserA() {
    return User.builder()
            .email("test@test.tea")
            .firstName("First Name")
            .lastName("Last Name")
            .role(Role.USER)
            .password("PW")
            .build();
  }

  /**
   * Creates a user object for testing.
   *
   * @return a user object
   */
  public static User createUserB() {
    return User.builder()
            .email("test@test.tea")
            .firstName("First Name")
            .lastName("Last Name")
            .role(Role.USER)
            .password("PW")
            .build();
  }

  /**
   * Creates an achievement object for testing.
   *
   * @return an achievement object
   */
  public static Achievement createAchievementA() {

    return Achievement.builder()
            .id(1)
            .category(AchievementCategory.AMOUNT_SAVED)
            .description("Save up a specific amount of money through Sparesti.")
            .thresholds(List.of(new Integer[]{100, 200, 500, 1000, 5000}))
            .build();
  }

  /**
   * Creates an achievement object for testing.
   *
   * @return an achievement object
   */
  public static Achievement createAchievementB() {

    return Achievement.builder()
            .id(1)
            .category(AchievementCategory.NUMBER_OF_CHALLENGES_COMPLETED)
            .description("Save up a specific amount of money through Sparesti.")
            .thresholds(List.of(new Integer[]{100, 200, 500, 1000, 5000}))
            .build();
  }


  /**
   * Creates a badge object for testing.
   *
   * @return a badge object
   */
  public static Badge createBadgeA() {

    return Badge.builder()
            .id(1L)
            .user(createUserA())
            .achievement(createAchievementA())
            .achievedDate(LocalDate.of(2024, 4, 3))
            .level(2)
            .build();
  }

  /**
   * Creates a badge object for testing.
   *
   * @return a badge object
   */
  public static Badge createBadgeB() {

    return Badge.builder()
            .id(2L)
            .user(createUserA())
            .achievement(createAchievementA())
            .achievedDate(LocalDate.of(2024, 4, 5))
            .level(2)
            .build();
  }

  /**
   * Creates a badge object for testing.
   *
   * @return a badge object
   */
  public static BadgePreviewDto createBadgePreviewDto() {
    return BadgePreviewDto.builder()
            .achievement(AchievementCategory.AMOUNT_SAVED)
            .threshold(createAchievementA().getThresholds().get(2))
            .achievementDate(LocalDate.of(2024, 4, 3))
            .build();
  }

  /**
   * Creates a badge object for testing.
   *
   * @return a badge object
   */
  public static BadgePreviewDto createBadgePreviewDto1() {

    return BadgePreviewDto.builder()
            .achievement(AchievementCategory.AMOUNT_SAVED)
            .threshold(createAchievementA().getThresholds().get(3))
            .achievementDate(LocalDate.of(2024, 4, 4))
            .build();
  }

  /**
   * Creates a badge object for testing.
   *
   * @return a badge object
   */
  public static BadgePreviewDto createBadgePreviewDto2() {

    return BadgePreviewDto.builder()
            .achievement(AchievementCategory.AMOUNT_SAVED)
            .threshold(createAchievementA().getThresholds().get(4))
            .achievementDate(LocalDate.of(2024, 4, 5))
            .build();
  }


  /**
   * Creates a badge object for testing.
   *
   * @return a badge object
   */
  public static BadgeCreateDto createBadgeCreateDto() {

    return BadgeCreateDto.builder()
            .achievement(BadgeUtility.createAchievementA())
            .achievementDate(LocalDate.of(2024, 4, 3))
            .level(1)
            .build();
  }


  /**
   * Creates a badge object for testing.
   *
   * @return a badge object
   */
  public static BadgeIdDto createBadgeIdDto() {
    return BadgeIdDto.builder()
            .id(1L)
            .build();
  }


  /**
   * Creates a badge object for testing.
   *
   * @return a badge object
   */
  public static String createBadgePreviewDtoListJson() {
    return "[{"
            + "\"achievement\":\"AMOUNT_SAVED\","
            + "\"achievementDate\":\"2024-04-03\","
            + "\"threshold\":500.0"
            + "},"
            + "{"
            + "\"achievement\":\"AMOUNT_SAVED\","
            + "\"achievementDate\":\"2024-04-04\","
            + "\"threshold\":1000.0"
            + "},"
            + "{"
            + "\"achievement\":\"AMOUNT_SAVED\","
            + "\"achievementDate\":\"2024-04-05\","
            + "\"threshold\":5000.0"
            + "}]";
  }


  /**
   * Creates an achievement category object for testing.
   *
   * @return an achievement category object
   */
  public static AchievementCategory createAchievementCategoryA() {
    return AchievementCategory.AMOUNT_SAVED;
  }

  /**
   * Creates an achievement preview dto object for testing.
   *
   * @return an achievement preview dto object
   */
  public static AchievementPreviewDto createAchievementPreviewDtoA() {
    return AchievementPreviewDto.builder()
            .category(AchievementCategory.AMOUNT_SAVED)
            .description("Save up a specific amount of money through Sparesti.")
            .thresholds(List.of(new Integer[]{100, 200, 500, 1000, 5000}))
            .build();
  }
}
