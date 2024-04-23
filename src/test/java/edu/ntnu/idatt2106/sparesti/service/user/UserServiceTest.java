package edu.ntnu.idatt2106.sparesti.service.user;

import edu.ntnu.idatt2106.sparesti.dto.user.UserDetailsDto;
import edu.ntnu.idatt2106.sparesti.dto.user.UserInfoDto;
import edu.ntnu.idatt2106.sparesti.dto.user.edit.FirstNameChangeDto;
import edu.ntnu.idatt2106.sparesti.dto.user.edit.IncomeChangeDto;
import edu.ntnu.idatt2106.sparesti.dto.user.edit.LastNameChangeDto;
import edu.ntnu.idatt2106.sparesti.dto.user.edit.LivingStatusChangeDto;
import edu.ntnu.idatt2106.sparesti.model.challenge.util.ChallengeUtility;
import edu.ntnu.idatt2106.sparesti.model.user.User;
import edu.ntnu.idatt2106.sparesti.model.user.UserInfo;
import java.util.Optional;
import edu.ntnu.idatt2106.sparesti.repository.user.UserInfoRepository;
import edu.ntnu.idatt2106.sparesti.repository.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


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
  private UserInfoRepository userInfoRepository;

  User user;

  @BeforeEach
  void setUp() {
    user = ChallengeUtility.createUserA();
  }

  @Test
  void Service_EditPassword_ReturnsSavedPassword() {
    //TODO, find a way to test this damn thing.
  }

  @Test
  void Service_EditFirstName_ReturnsSavedFirstName() {
    // Arrange
    FirstNameChangeDto firstNameChangeDto = UserServiceUtility.createFirstNameChangeDto("Jane");
    when(userRepository.save(user)).thenReturn(user);
    when(userRepository.findUserByEmailIgnoreCase(user.getEmail())).thenReturn(Optional.of(user));

    // Act
    userService.editFirstName(firstNameChangeDto, user.getEmail());

    // Assert
    assertEquals("Jane", user.getFirstName());
  }

  @Test
  void Service_EditLastName_ReturnSavedLastName() {
    //Arrange
    LastNameChangeDto lastNameChangeDto = UserServiceUtility.createLastNameChangeDto("Smith");
    when(userRepository.save(user)).thenReturn(user);
    when(userRepository.findUserByEmailIgnoreCase(user.getEmail())).thenReturn(Optional.of(user));

    //Act
    userService.editLastName(lastNameChangeDto, user.getEmail());

    //Assert
    assertEquals("Smith", user.getLastName());
  }

  @Test
  void Service_EditIncome_ReturnSavedLastIncome() {
    //Arrange
    IncomeChangeDto incomeChangeDto = UserServiceUtility.createIncomeChangeDto(1000);
    when(userRepository.save(user)).thenReturn(user);
    when(userRepository.findUserByEmailIgnoreCase(user.getEmail())).thenReturn(Optional.of(user));

    //Act
    userService.editIncome(incomeChangeDto, user.getEmail());

    //Assert
    assertEquals(1000, user.getUserInfo().getIncome());
  }

  @Test
  void Service_EditLivingStatus_ReturnSavedLivingStatus() {
    //Arrange
    LivingStatusChangeDto livingStatusChangeDto = UserServiceUtility.createLivingStatusChangeDto(1);
    when(userRepository.save(user)).thenReturn(user);
    when(userRepository.findUserByEmailIgnoreCase(user.getEmail())).thenReturn(Optional.of(user));

    //Act
    userService.editLivingStatus(livingStatusChangeDto, user.getEmail());

    //Assert
    assertEquals(1, user.getUserInfo().getLivingStatus().ordinal());
  }

  @Test
  void Service_GetUserDetails_ReturnCorrectUserDetails() {
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
  void Service_AddUserInfo_DoesNotThrowException() {
    //Arrange
    user = ChallengeUtility.createUserB();
    UserInfoDto userInfoDto = ChallengeUtility.createUserInfoDtoA();
    when(userRepository.findUserByEmailIgnoreCase(user.getEmail())).thenReturn(Optional.of(user));
    when(userInfoRepository.save(any(UserInfo.class))).thenReturn(user.getUserInfo());


    //Assert
    assertDoesNotThrow(() -> userService.addUserInfo(userInfoDto, user.getEmail()));
  }
}