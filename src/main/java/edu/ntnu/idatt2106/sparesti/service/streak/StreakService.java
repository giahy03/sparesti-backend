package edu.ntnu.idatt2106.sparesti.service.streak;

import edu.ntnu.idatt2106.sparesti.dto.streak.StreakRequestDto;
import edu.ntnu.idatt2106.sparesti.dto.streak.StreakResponseDto;
import edu.ntnu.idatt2106.sparesti.model.streak.Streak;
import edu.ntnu.idatt2106.sparesti.model.user.User;
import edu.ntnu.idatt2106.sparesti.repository.user.UserRepository;
import edu.ntnu.idatt2106.sparesti.service.user.UserService;
import java.util.Optional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Service class for managing streaks of users.
 *
 * @author Ramtin Samavat
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class StreakService {

  private final UserService userService;

  private final UserRepository userRepository;

  /**
   * The method updates the streak of a user.
   *
   * @param streakRequestDto The streak request DTO containing the update information.
   * @param email            The email of the user whose streak needs to be updated.
   */
  public void updateStreak(@NonNull StreakRequestDto streakRequestDto, String email) {

    User user = userService.findUser(email);

    Streak streak = Optional.ofNullable(user.getStreak()).orElseGet(() -> initializeStreak(user));

    if (streakRequestDto.isIncrement()) {
      streak.setNumberOfDays(streak.getNumberOfDays() + 1);
    } else {
      streak.setNumberOfDays(0);
    }

    userRepository.save(user);
  }

  /**
   * The method retrieves the streak of a user.
   *
   * @param email The email of the user whose streak needs to be retrieved.
   * @return The streak response DTO containing the user's streak.
   */
  public StreakResponseDto getStreak(@NonNull String email) {
    User user = userService.findUser(email);

    Streak streak = Optional.ofNullable(user.getStreak()).orElseGet(() -> initializeStreak(user));

    return StreakResponseDto.builder()
        .numberOfDays(streak.getNumberOfDays())
        .build();
  }

  /**
   * The method initializes a new streak for the given user.
   *
   * @param user The user for whom the streak is being initialized.
   * @return The initialized streak object.
   */
  private Streak initializeStreak(User user) {
    Streak streak = Streak.builder()
        .numberOfDays(0)
        .user(user)
        .build();
    user.setStreak(streak);
    return streak;
  }
}
