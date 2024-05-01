package edu.ntnu.idatt2106.sparesti.model.user;

import edu.ntnu.idatt2106.sparesti.model.analysis.ssb.SsbLivingStatus;
import edu.ntnu.idatt2106.sparesti.model.challenge.util.ChallengeUtility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
    user = ChallengeUtility.createUser1();
    userInfo = ChallengeUtility.createUserInfoA();

    user.setUserInfo(userInfo);
    userInfo.setUser(user);
  }


  @Test
  @DisplayName("Test that userInfo constructor returns a userInfo object")
  void UserInfo_UserInfoConstructor_ReturnUserInfo() {
    // Arrange
    double expectedIncome = 1000;
    SsbLivingStatus expectedLivingStatus = SsbLivingStatus.fromInteger(1);
    User expectedUser = user;

    // Act
    UserInfo actualUserInfo = UserInfo.builder()
            .user(expectedUser)
            .income(expectedIncome)
            .livingStatus(expectedLivingStatus)
            .build();

    // Assert
    assertEquals(expectedIncome, actualUserInfo.getIncome());
    assertEquals(expectedLivingStatus, actualUserInfo.getLivingStatus());
    assertEquals(expectedUser.getEmail(), actualUserInfo.getUser().getEmail());
  }


  @Test
  @DisplayName("Test UserInfo constructor with no args returns a userInfo object.")
  void UserInfo_UserInfoConstructorWithNoArgs_ReturnUserInfo() {
    // Arrange
    double expectedIncome = 0;
    SsbLivingStatus expectedLivingStatus = SsbLivingStatus.fromInteger(0);
    User expectedUser = user;

    // Act
    UserInfo actualUserInfo = new UserInfo();
    actualUserInfo.setIncome(expectedIncome);
    actualUserInfo.setLivingStatus(SsbLivingStatus.fromInteger(0));
    actualUserInfo.setUser(expectedUser);

    // Assert
    assertEquals(expectedIncome, actualUserInfo.getIncome());
    assertEquals(expectedLivingStatus, actualUserInfo.getLivingStatus());
    assertEquals(expectedUser.getEmail(), actualUserInfo.getUser().getEmail());
  }

}