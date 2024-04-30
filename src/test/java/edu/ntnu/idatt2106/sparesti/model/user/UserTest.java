package edu.ntnu.idatt2106.sparesti.model.user;

import edu.ntnu.idatt2106.sparesti.model.challenge.util.ChallengeUtility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
  User testUser;

  /**
   * Arrange a testUser for every method.
   */
  @BeforeEach
  void setUp() {
    testUser = ChallengeUtility.createUserD();
  }

  @Test
  void User_UserConstructor_ReturnUser() {
    //Arrange
    String expectedUsername = "example@guide";
    String expectedPassword = "password";
    String expectedEmail = "example@guide";
    String expectedName = "Example";
    String expectedSurName = "Guide";
    Role expectedRole = Role.USER;

    //Act
    User actual = ChallengeUtility.createUserB();

    // Assert
    assertEquals(expectedUsername, actual.getUsername());
    assertEquals(expectedPassword, actual.getPassword());
    assertEquals(expectedEmail, actual.getEmail());
    assertEquals(expectedName, actual.getFirstName());
    assertEquals(expectedSurName, actual.getLastName());
    assertEquals(expectedRole, actual.getRole());
  }

  @Test
  void User_UserConstructorWithNoArgs_ReturnUser() {

    //Arrange
    User user = new User();
    String expectedName = "Jake";

    //Act
    user.setEmail(expectedName);
    String actualName = user.getEmail();

    //Assert
    assertEquals(expectedName, actualName);
  }

  @Test
  public void User_UserConstructWithNull_ThrowsException() {
    assertThrows(NullPointerException.class, () ->
            User.builder()
                    .email(null)
                    .build()
    );
  }

  @Test
  void User_GetUsername_ReturnUsername() {

    //Arrange
    String expected = "Anna@gmail.com";

    //Act
    String actual = testUser.getUsername();

    //Assert
    assertEquals(expected, actual);
  }

  @Test
  void User_GetPassword_ReturnPassword() {
    //Arrange
    String expected = "password";

    //Act
    String actual = testUser.getPassword();

    //Assert
    assertEquals(expected, actual);
  }

  @Test
  void User_GetEmail_ReturnEmail() {
    //Arrange
    String expected = "Anna@gmail.com";

    //Act
    String actual = testUser.getEmail();

    //Assert
    assertEquals(expected, actual);
  }

  @Test
  void User_GetName_ReturnName() {
    //Arrange
    String expected = "Anna";

    //Act
    String actual = testUser.getFirstName();

    //Assert
    assertEquals(expected, actual);
  }

  @Test
  void User_GetSurname_ReturnSurname() {
    //Arrange
    String expected = "Guide";

    //Act
    String actual = testUser.getLastName();

    //Assert
    assertEquals(expected, actual);
  }

  @Test
  void User_GetRole_ReturnRole() {
    //Arrange
    Role expected = Role.USER;

    //Act
    Role actual = testUser.getRole();

    //Assert
    assertEquals(expected, actual);
  }


  @Test
  void User_GetSimpleAuthority_ReturnRole() {

    //Act
    List<? extends GrantedAuthority> actual = testUser.getAuthorities().stream().toList();

    //Assert
    assertFalse(actual.isEmpty());
  }

  @Test
  void User_isEnabled_True() {
    //Arrange
    boolean expected = true;

    //Act
    boolean actual = testUser.isEnabled();

    //Assert
    assertEquals(expected, actual);
  }

  @Test
  void User_isCredentialsNonExpired_True() {
    //Arrange
    boolean expected = true;

    //Act
    boolean actual = testUser.isCredentialsNonExpired();

    //Assert
    assertEquals(expected, actual);
  }

  @Test
  void User_isAccountNonLocked_True() {
    //Arrange
    boolean expected = true;

    //Act
    boolean actual = testUser.isAccountNonLocked();

    //Assert
    assertEquals(expected, actual);
  }

  @Test
  void User_isAccountNonExpired_True() {
    //Arrange
    boolean expected = true;

    //Act
    boolean actual = testUser.isAccountNonExpired();

    //Assert
    assertEquals(expected, actual);
  }


  @Test
  void User_SetPassword_ReturnSavedPassword() {
    //Arrange
    String expected = "newPassword";

    //Act
    testUser.setPassword("newPassword");
    String actual = testUser.getPassword();

    //Assert
    assertEquals(expected, actual);
  }

  @Test
  void User_SetPasswordToNull_ReturnException() {
    //Arrange
    String expected = "password is marked non-null but is null";

    //Act
    Exception actual = assertThrows(Exception.class, () -> {
      testUser.setPassword(null);
    });

    //Assert
    assertEquals(expected, actual.getMessage());

  }

  @Test
  void User_SetEmail_ReturnSavedEmail() {
    //Arrange
    String expected = "test@Email.com";

    //Act
    testUser.setEmail("test@Email.com");
    String actual = testUser.getEmail();

    //Assert
    assertEquals(expected, actual);
  }

  @Test
  void User_SetName_ReturnSavedName() {
    //Arrange
    String expected = "testName";

    //Act
    testUser.setFirstName("testName");
    String actual = testUser.getFirstName();

    //Assert
    assertEquals(expected, actual);
  }

  @Test
  void User_SetSurname_ReturnSavedSurname() {
    //Arrange
    String expected = "Timothy";

    //Act
    testUser.setLastName("Timothy");
    String actual = testUser.getLastName();

    //Assert
    assertEquals(expected, actual);
  }

  @Test
  void User_SetRole_ReturnSavedRole() {
    //Arrange
    Role expected = Role.ADMIN;

    //Act
    testUser.setRole(expected);
    Role actual = testUser.getRole();

    //Assert
    assertEquals(expected, actual);
  }

}