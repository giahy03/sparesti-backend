package edu.ntnu.idatt2106.sparesti.model.user;

import edu.ntnu.idatt2106.sparesti.model.challenge.util.ChallengeUtility;
import edu.ntnu.idatt2106.sparesti.model.streak.Streak;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
  User user;

  /**
   * Arrange a testUser for every method.
   */
  @BeforeEach
  void setUp() {
    user = ChallengeUtility.createUserD();
  }

  @Test
  @DisplayName("Test that user constructor returns a user object")
  void User_UserConstructor_ReturnUser() {
    //Arrange
    String expectedEmail = "example@guide";
    String expectedName = "Example";
    String expectedSurName = "Guide";
    String expectedPassword = "password";
    Role expectedRole = Role.USER;
    UserInfo expectedUserInfo = ChallengeUtility.createUserInfoA();
    Streak expectedStreak = ChallengeUtility.createStreak1();

    //Act
    User actualUser = User.builder()
            .email(expectedEmail)
            .firstName(expectedName)
            .lastName(expectedSurName)
            .password(expectedPassword)
            .role(expectedRole)
            .streak(expectedStreak)
            .userInfo(expectedUserInfo)
            .build();

    // Assert
    assertEquals(expectedEmail, actualUser.getUsername());
    assertEquals(expectedPassword, actualUser.getPassword());
    assertEquals(expectedEmail, actualUser.getEmail());
    assertEquals(expectedName, actualUser.getFirstName());
    assertEquals(expectedSurName, actualUser.getLastName());
    assertEquals(expectedRole, actualUser.getRole());
    assertEquals(expectedUserInfo, actualUser.getUserInfo());
    assertEquals(expectedStreak, actualUser.getStreak());
  }

  @Test
  void User_UserConstructorWithNoArgs_ReturnUser() {
    //Arrange

    User user = new User();
    String expectedEmail = "example@guide";
    String expectedName = "Example";
    String expectedSurName = "Guide";
    String expectedPassword = "password";
    Role expectedRole = Role.USER;
    UserInfo expectedUserInfo = ChallengeUtility.createUserInfoA();
    Streak expectedStreak = ChallengeUtility.createStreak1();


    //Act
    user.setEmail(expectedEmail);
    user.setFirstName(expectedName);
    user.setLastName(expectedSurName);
    user.setPassword(expectedPassword);
    user.setRole(expectedRole);
    user.setUserInfo(expectedUserInfo);
    user.setStreak(expectedStreak);

    //Assert
    assertEquals(expectedEmail, user.getUsername());
    assertEquals(expectedPassword, user.getPassword());
    assertEquals(expectedEmail, user.getEmail());
    assertEquals(expectedName, user.getFirstName());
    assertEquals(expectedSurName, user.getLastName());
    assertEquals(expectedRole, user.getRole());
    assertEquals(expectedUserInfo, user.getUserInfo());
    assertEquals(expectedStreak, user.getStreak());
  }

  @Test
  void User_UserConstructWithNull_ThrowsException() {
    User user = new User();
    assertThrows(NullPointerException.class, () -> user.setEmail(null));
  }

  @Test
  void User_GetSimpleAuthority_ReturnRole() {

    //Act
    List<? extends GrantedAuthority> actual = user.getAuthorities().stream().toList();

    //Assert
    assertFalse(actual.isEmpty());
  }

  @Test
  void User_isEnabled_True() {
    //Arrange
    boolean expected = true;

    //Act
    boolean actual = user.isEnabled();

    //Assert
    assertEquals(expected, actual);
  }

  @Test
  void User_isCredentialsNonExpired_True() {
    //Arrange
    boolean expected = true;

    //Act
    boolean actual = user.isCredentialsNonExpired();

    //Assert
    assertEquals(expected, actual);
  }

  @Test
  void User_isAccountNonLocked_True() {
    //Arrange
    boolean expected = true;

    //Act
    boolean actual = user.isAccountNonLocked();

    //Assert
    assertEquals(expected, actual);
  }

  @Test
  void User_isAccountNonExpired_True() {
    //Arrange
    boolean expected = true;

    //Act
    boolean actual = user.isAccountNonExpired();

    //Assert
    assertEquals(expected, actual);
  }


  @Test
  void User_SetPasswordToNull_ReturnException() {
    //Arrange
    String expected = "password is marked non-null but is null";

    //Act
    Exception actual = assertThrows(Exception.class, () -> {
      user.setPassword(null);
    });

    //Assert
    assertEquals(expected, actual.getMessage());

  }

  @Test
  void User_SetRole_ReturnSavedRole() {
    //Arrange
    Role expected = Role.ADMIN;

    //Act
    user.setRole(expected);
    Role actual = user.getRole();

    //Assert
    assertEquals(expected, actual);
  }

}