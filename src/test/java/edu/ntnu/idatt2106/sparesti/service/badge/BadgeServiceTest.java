package edu.ntnu.idatt2106.sparesti.service.badge;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import edu.ntnu.idatt2106.sparesti.dto.achievementstats.AchievementPreviewDto;
import edu.ntnu.idatt2106.sparesti.dto.badge.BadgeCreateDto;
import edu.ntnu.idatt2106.sparesti.dto.badge.BadgePreviewDto;
import edu.ntnu.idatt2106.sparesti.mapper.AchievementMapper;
import edu.ntnu.idatt2106.sparesti.mapper.BadgeMapper;
import edu.ntnu.idatt2106.sparesti.model.badge.Achievement;
import edu.ntnu.idatt2106.sparesti.model.badge.Badge;
import edu.ntnu.idatt2106.sparesti.model.badge.util.BadgeUtility;
import edu.ntnu.idatt2106.sparesti.model.user.User;
import edu.ntnu.idatt2106.sparesti.repository.AchievementRepository;
import edu.ntnu.idatt2106.sparesti.repository.BadgeRepository;
import edu.ntnu.idatt2106.sparesti.repository.user.UserRepository;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;


/**
 * Test class for badge service class.
 *
 * @author Hanne-Sofie Søreide
 */
@ExtendWith(MockitoExtension.class)
class BadgeServiceTest {

  @InjectMocks
  BadgeService badgeService;

  @Mock
  BadgeRepository badgeRepository;
  @Mock
  BadgeMapper badgeMapper;

  @Mock
  AchievementMapper achievementMapper;

  @Mock
  AchievementRepository achievementRepository;

  @Mock
  UserRepository userRepository;

  Principal principal;

  Pageable pageable;

  private Badge badge;


  @BeforeEach
  void setUp() {
    badge = BadgeUtility.createBadgeA();
    principal = () -> "test@test.tea";
  }

  @DisplayName("Test for getBadgeById")
  @Test
  void badge_getBadgeById_ReturnBadge() {

    // Arrange
    when(badgeRepository.findById(1L)).thenReturn(Optional.of(badge));
    when(badgeMapper.mapToBadgePreviewDto(badge)).thenReturn(BadgeUtility.createBadgePreviewDto());

    // Act
    BadgePreviewDto badgePreviewDto = badgeService.getBadgeById(BadgeUtility.createBadgeIdDto());

    // Assert
    assertNotNull(badgePreviewDto);
  }


  @DisplayName("Test for getAllBadgesByEmail")
  @Test
  void badge_getAllBadgesByEmail_ReturnBadges() {

    // Arrange
    List<Badge> badgeList = new ArrayList<>();
    badgeList.add(BadgeUtility.createBadgeA());
    badgeList.add(BadgeUtility.createBadgeB());

    when(badgeRepository.findAllByUser_Email(principal.getName(), pageable)).thenReturn(badgeList);

    // Act
    List<BadgePreviewDto> returnedPreviewBadges =
            badgeService.getAllBadgesByEmail(principal, pageable);

    // Assert
    assertThat(returnedPreviewBadges.size()).isEqualTo(2);
  }

  @DisplayName("Test for deleteBadgeById")
  @Test
  void badge_deleteBadgeById_DeleteBadge() {

    // Act and assert
    assertDoesNotThrow(() -> badgeService
            .deleteBadgeById(principal, BadgeUtility.createBadgeIdDto()));

  }

  @DisplayName("Test for createBadge")
  @Test
  void badge_createBadge_CreateBadge() {

    // Arrange
    User user = BadgeUtility.createUserA();
    BadgeCreateDto badgeCreateDto =
            BadgeUtility.createBadgeCreateDto();
    when(userRepository
            .findUserByEmailIgnoreCase(principal.getName())).thenReturn(Optional.of(user));
    when(badgeMapper.mapToBadge(badgeCreateDto, user)).thenReturn(badge);
    when(badgeRepository.save(badge)).thenReturn(badge);
    when(badgeMapper.mapToBadgePreviewDto(badge)).thenReturn(BadgeUtility.createBadgePreviewDto());

    // Act and assert
    assertDoesNotThrow(() -> badgeService.createBadge(badgeCreateDto, principal));
    assertNotNull(badgeService.createBadge(badgeCreateDto, principal));

  }

  @Test
  void badge_getAchievementOfCategory_ReturnsBadge() {
    // Arrange
    when(achievementRepository.findByCategory(BadgeUtility.createAchievementCategoryA()))
            .thenReturn(Optional.of(BadgeUtility.createAchievementA()));

    // Act and assert
    assertNotNull(badgeService.getAchievementOfCategory(
            BadgeUtility.createAchievementCategoryA()));
  }

  @Test
  void badge_getAchievementPreviews_ReturnsAchievementPreviews() {
    // Arrange
    when(achievementRepository.findAll()).thenReturn(List.of(
            BadgeUtility.createAchievementA(), BadgeUtility.createAchievementB()));
    when(achievementMapper.mapToAchievementPreviewDto(any(Achievement.class)))
            .thenReturn(BadgeUtility.createAchievementPreviewDtoA());

    // Act
    List<AchievementPreviewDto> achievementPreviews = badgeService.getAchievementPreviews();

    // Assert
    assertThat(achievementPreviews.size()).isEqualTo(2);
  }


}
