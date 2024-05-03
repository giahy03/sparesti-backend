package edu.ntnu.idatt2106.sparesti.model.badge;

import edu.ntnu.idatt2106.sparesti.model.badge.util.BadgeUtility;
import edu.ntnu.idatt2106.sparesti.model.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test class for the Badge model class.
 *
 * @author Hanne-Sofie Søreide
 */
public class BadgeTest {

    Badge badge;

    @BeforeEach
    void setUp() {
        badge = BadgeUtility.createBadgeA();
    }

    @DisplayName("Test for the getter methods of Badge")
    @Test
    void Badge_getters_returnsExpectedValues() {

        // Arrange
        Long expectedId = 1L;
        User expectedUser = BadgeUtility.createUserA();
        Achievement expectedAchievement = BadgeUtility.createAchievementA();
        LocalDate expectedDate = LocalDate.of(2024, 4, 3);

        // Assert
        assertEquals(expectedId, badge.getId());
        assertEquals(expectedUser.getEmail(), badge.getUser().getEmail());
        assertEquals(expectedAchievement.getCategory(), badge.getAchievement().getCategory());
        assertEquals(expectedDate, badge.getAchievedDate());
    }

    @DisplayName("Test for the setter methods of Badge")
    @Test
    void Badge_setters_returnsExpectedValues() {

        // Arrange
        Long expectedId = 2L;
        User expectedUser = BadgeUtility.createUserB();
        Achievement expectedAchievement = BadgeUtility.createAchievementB();
        LocalDate expectedDate = LocalDate.of(2024, 4, 10);
        int expectedLevel = 3;

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

}
