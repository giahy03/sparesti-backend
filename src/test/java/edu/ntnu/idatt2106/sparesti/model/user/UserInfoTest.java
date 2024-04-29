package edu.ntnu.idatt2106.sparesti.model.user;

import edu.ntnu.idatt2106.sparesti.model.analysis.ssb.SsbLivingStatus;
import edu.ntnu.idatt2106.sparesti.model.challenge.util.ChallengeUtility;
import edu.ntnu.idatt2106.sparesti.service.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * A test class for the UserInfo class.
 *
 * @author Jeffrey Yaw Annor Tabiri
 * @version 1.0
 * @see UserInfo
 */
class UserInfoTest {
  UserInfo userInfo;
  User user;

  @BeforeEach
  void setUp() {
    user = ChallengeUtility.createUserD();
    userInfo = ChallengeUtility.createUserInfoD(user);

  }

  @Test
  void UserInfo_GetIncome_ReturnsIncome() {
    // Arrange
    double expectedIncome = 1000;

    // Act
    double actualIncome = userInfo.getIncome();

    // Assert
    assertEquals(expectedIncome, actualIncome);
  }

  @Test
  void UserInfo_GetLivingStatus_ReturnsIncome() {
    // Arrange
    SsbLivingStatus expectedLivingStatus = SsbLivingStatus.fromInteger(1);

    // Act
    SsbLivingStatus actualLivingStatus = userInfo.getLivingStatus();


    // Assert
    assertEquals(expectedLivingStatus, actualLivingStatus);
  }

  @Test
  void UserInfo_GetUser_ReturnUser() {
    // Arrange
    userInfo.setUser(user);
    User expectedUser = user;

    // Act
    User actualUser = userInfo.getUser();

    // Assert
    assertEquals(expectedUser.getEmail(), actualUser.getEmail());
  }

  @Test
  void UserInfo_SetIncome_ReturnSavedIncome() {
    // Arrange
    double expectedIncome = 2000;

    // Act
    userInfo.setIncome(2000);
    double actualIncome = userInfo.getIncome();

    // Assert
    assertEquals(expectedIncome, actualIncome);
  }

  @Test
  void UserInfo_SetLivingStatus_ReturnLivingStatus() {
    // Arrange
    SsbLivingStatus expectedLivingStatus = SsbLivingStatus.fromInteger(2);

    // Act
    userInfo.setLivingStatus(SsbLivingStatus.fromInteger(2));
    SsbLivingStatus actualLivingStatus = userInfo.getLivingStatus();

    // Assert
    assertEquals(expectedLivingStatus, actualLivingStatus);
  }

  @Test
  void UserInfo_SetUser_ReturnSavedUser() {
    // Arrange
    User expectedUser = ChallengeUtility.createUserB();

    // Act
    userInfo.setUser(user);
    User actualUser = userInfo.getUser();

    // Assert
    assertEquals(expectedUser.getEmail(), actualUser.getEmail());
  }
}