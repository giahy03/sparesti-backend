package edu.ntnu.idatt2106.sparesti.model.badge;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import edu.ntnu.idatt2106.sparesti.model.badge.util.BadgeUtility;
import edu.ntnu.idatt2106.sparesti.model.user.User;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;





/**
 * Test class for the Badge model class.
 *
 * @author Hanne-Sofie Søreide
 */
class BadgeTest {

  Badge badge;

  @BeforeEach
  void setUp() {
    badge = BadgeUtility.createBadgeA();
  }

  @DisplayName("Test for the getter methods of Badge")
  @Test
  void badge_getters_returnsExpectedValues() {

    // Arrange
    final Long expectedId = 1L;
    final User expectedUser = BadgeUtility.createUserA();
    final Achievement expectedAchievement = BadgeUtility.createAchievementA();
    final LocalDate expectedDate = LocalDate.of(2024, 4, 3);

    // Assert
    assertEquals(expectedId, badge.getId());
    assertEquals(expectedUser.getEmail(), badge.getUser().getEmail());
    assertEquals(expectedAchievement.getCategory(), badge.getAchievement().getCategory());
    assertEquals(expectedDate, badge.getAchievedDate());
  }

  @DisplayName("Test for the setter methods of Badge")
  @Test
  void badge_setters_returnsExpectedValues() {

    // Arrange
    final Long expectedId = 2L;
    final User expectedUser = BadgeUtility.createUserB();
    final Achievement expectedAchievement = BadgeUtility.createAchievementB();
    final LocalDate expectedDate = LocalDate.of(2024, 4, 10);
    final int expectedLevel = 3;

    // Act
    badge.setId(2L);
    badge.setUser(expectedUser);
    badge.setAchievement(expectedAchievement);
    badge.setAchievedDate(expectedDate);
    badge.setLevel(expectedLevel);

    // Assert
    assertEquals(expectedId, badge.getId());
    assertEquals(expectedUser.getEmail(), badge.getUser().getEmail());
    assertEquals(expectedAchievement.getCategory(), badge.getAchievement().getCategory());
    assertEquals(expectedDate, badge.getAchievedDate());
    assertEquals(expectedLevel, badge.getLevel());
  }

  @DisplayName("Test for setters expecting exceptions setting null")
  @Test
  void badge_setters_expectingExceptions() {

    // Act and Assert
    assertThrows(NullPointerException.class, () -> badge.setUser(null));
    assertThrows(NullPointerException.class, () -> badge.setAchievement(null));
    assertThrows(NullPointerException.class, () -> badge.setAchievedDate(null));
  }

}
