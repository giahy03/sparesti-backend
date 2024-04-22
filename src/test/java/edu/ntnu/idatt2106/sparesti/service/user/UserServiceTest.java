package edu.ntnu.idatt2106.sparesti.service.user;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

  @InjectMocks
  private UserService userService;

  @Mock
  private UserRepository userRepository;

  @Mock
  private UserInfoRepository userInfoRepository;

  @Mock
  private PasswordEncoder passwordEncoder;



  @Test
  void Service_EditPassword_ReturnsSavedPassword() {

    // Arrange
    when()
    // Act
    // Assert

  }

  @Test
  void editFistName() {
  }

  @Test
  void editLastName() {
  }

  @Test
  void editIncome() {
  }

  @Test
  void editLivingStatus() {
  }

  @Test
  void getUserDetails() {
  }

  @Test
  void addUserInfo() {
  }

}