package edu.ntnu.idatt2106.sparesti.service.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import edu.ntnu.idatt2106.sparesti.dto.user.UserDetailsDto;
import edu.ntnu.idatt2106.sparesti.dto.user.UserInfoDto;
import edu.ntnu.idatt2106.sparesti.dto.user.edit.FirstNameChangeDto;
import edu.ntnu.idatt2106.sparesti.dto.user.edit.IncomeChangeDto;
import edu.ntnu.idatt2106.sparesti.dto.user.edit.LastNameChangeDto;
import edu.ntnu.idatt2106.sparesti.dto.user.edit.LivingStatusChangeDto;
import edu.ntnu.idatt2106.sparesti.exception.user.UserNotFoundException;
import edu.ntnu.idatt2106.sparesti.model.challenge.util.ChallengeUtility;
import edu.ntnu.idatt2106.sparesti.model.user.User;
import edu.ntnu.idatt2106.sparesti.model.user.UserInfo;
import edu.ntnu.idatt2106.sparesti.repository.user.UserRepository;
import edu.ntnu.idatt2106.sparesti.service.email.EmailVerificationService;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * A test for the user-service class which is responsible for handling user-related operations.
 *
 * @author Jeffrey Yaw Annor Tabiri
 * @version 1.0
 * @see UserService
 */
@ExtendWith(MockitoExtension.class)
class UserServiceTest {
  @InjectMocks
  private UserService userService;

  @Mock
  private UserRepository userRepository;

  @Mock
  private EmailVerificationService emailVerificationService;

  User user;

  UserInfo userInfo;

  @BeforeEach
  void setUp() {
    user = ChallengeUtility.createUser2();
    userInfo = ChallengeUtility.createUserInfoA();
    user.setUserInfo(userInfo);
    userInfo.setUser(user);
  }

  @Test
  @DisplayName("Service delete user should delete given user")
  void service_DeleteUserByEmail_DeletesUser() {
    // Arrange
    when(userRepository.findUserByEmailIgnoreCase(user.getEmail())).thenReturn(Optional.of(user));

    // Act
    userService.deleteUserByEmail(user.getEmail(), "123456");

    // Assert
    verify(userRepository).delete(user);
  }

  @Test
  @DisplayName("Service edit first name should return saved first name")
  void service_EditFirstName_ReturnsSavedFirstName() {
    // Arrange
    FirstNameChangeDto firstNameChangeDto = UserServiceUtility.createFirstNameChangeDto("Jane");
    when(userRepository.save(user)).thenReturn(user);
    when(userRepository.findUserByEmailIgnoreCase(user.getEmail())).thenReturn(Optional.of(user));

    // Act
    userService.editFirstName(firstNameChangeDto, user.getEmail());

    // Assert
    verify(userRepository).save(user);
    assertEquals("Jane", user.getFirstName());
  }


  @Test
  @DisplayName("Service edit last name should return saved last name")
  void service_EditLastName_ReturnSavedLastName() {
    //Arrange
    LastNameChangeDto lastNameChangeDto = UserServiceUtility.createLastNameChangeDto("Smith");
    when(userRepository.save(user)).thenReturn(user);
    when(userRepository.findUserByEmailIgnoreCase(user.getEmail())).thenReturn(Optional.of(user));

    //Act
    userService.editLastName(lastNameChangeDto, user.getEmail());

    //Assert
    verify(userRepository).save(user);
    assertEquals("Smith", user.getLastName());
  }

  @Test
  @DisplayName("Service edit income should return saved last income")
  void service_EditIncome_ReturnSavedLastIncome() {
    //Arrange
    IncomeChangeDto incomeChangeDto = UserServiceUtility.createIncomeChangeDto(1000);
    when(userRepository.save(user)).thenReturn(user);
    when(userRepository.findUserByEmailIgnoreCase(user.getEmail())).thenReturn(Optional.of(user));

    //Act
    userService.editIncome(incomeChangeDto, user.getEmail());

    //Assert
    verify(userRepository).save(user);
    assertEquals(1000, user.getUserInfo().getIncome());
  }

  @Test
  @DisplayName("Service edit living status should return saved living status")
  void service_EditLivingStatus_ReturnSavedLivingStatus() {
    //Arrange
    LivingStatusChangeDto livingStatusChangeDto = UserServiceUtility.createLivingStatusChangeDto(1);
    when(userRepository.save(user)).thenReturn(user);
    when(userRepository.findUserByEmailIgnoreCase(user.getEmail())).thenReturn(Optional.of(user));

    //Act
    userService.editLivingStatus(livingStatusChangeDto, user.getEmail());

    //Assert
    verify(userRepository).save(user);
    assertEquals(1, user.getUserInfo().getLivingStatus().ordinal());
  }

  @Test
  @DisplayName("Service get user details should return actual user dto")
  void service_GetUserDetails_ReturnCorrectUserDetails() {
    //Arrange
    String expectedFirstName = user.getFirstName();
    String expectedLastName = user.getLastName();
    when(userRepository.findUserByEmailIgnoreCase(user.getEmail())).thenReturn(Optional.of(user));

    //Act
    UserDetailsDto userInfoDto = userService.getUserDetails(user.getEmail());
    String actualFirstName = userInfoDto.getFirstName();
    String actualLastName = userInfoDto.getLastName();

    //Assert
    assertEquals(expectedFirstName, actualFirstName);
    assertEquals(expectedLastName, actualLastName);
  }

  @Test
  @DisplayName("Service add user info throws exception if user already exists")
  void service_AddUserInfo_DoesNotThrowException() {

    //Arrange
    UserInfoDto userInfoDto = ChallengeUtility.createUserInfoDtoA();
    when(userRepository.findUserByEmailIgnoreCase(user.getEmail())).thenReturn(Optional.empty());

    //Assert
    assertThrows(UserNotFoundException.class,
            () -> userService.addUserInfo(userInfoDto, user.getEmail()));
  }

  @Test
  @DisplayName("Service add user info should return saved user info")
  void service_AddUserInfo_ReturnSavedUserInfo() {
    //Arrange
    UserInfoDto userInfoDto = ChallengeUtility.createUserInfoDtoA();
    when(userRepository.save(user)).thenReturn(user);
    when(userRepository.findUserByEmailIgnoreCase(user.getEmail())).thenReturn(Optional.of(user));

    //Act
    userService.addUserInfo(userInfoDto, user.getEmail());

    //Assert
    verify(userRepository).save(user);
    assertEquals(1000, user.getUserInfo().getIncome());
    assertEquals(1, user.getUserInfo().getLivingStatus().ordinal());
  }

}