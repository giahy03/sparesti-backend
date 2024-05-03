package edu.ntnu.idatt2106.sparesti.model.user;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;




/**
 * Tests the enum values.
 *
 * @author Jeffrey Yaw Annor Tabiri
 * @version 1.0
 */
class RoleTest {

  @DisplayName("Test for the enum values of Role")
  @Test
  void role_testEnumValues() {
    // Assert
    assertEquals("USER", Role.USER.name());
    assertEquals("ADMIN", Role.ADMIN.name());
  }

}