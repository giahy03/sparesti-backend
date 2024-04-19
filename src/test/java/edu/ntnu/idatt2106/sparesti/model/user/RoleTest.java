package edu.ntnu.idatt2106.sparesti.model.user;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the enum values.
 *
 * @author Jeffrey Yaw Annor Tabiri
 * @version 1.0
 */
class RoleTest {
  @Test
  void Role_testEnumValues() {
    // Assert
    Assertions.assertEquals("USER", Role.USER.name());
    assertEquals("ADMIN", Role.ADMIN.name());
  }

}