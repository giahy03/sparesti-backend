package edu.ntnu.idatt2106.sparesti.service.badge;

import edu.ntnu.idatt2106.sparesti.dto.achievementstats.AchievementPreviewDto;
import edu.ntnu.idatt2106.sparesti.dto.badge.BadgeCreateDto;
import edu.ntnu.idatt2106.sparesti.dto.badge.BadgeIdDto;
import edu.ntnu.idatt2106.sparesti.dto.badge.BadgePreviewDto;
import edu.ntnu.idatt2106.sparesti.exception.user.UserNotFoundException;
import edu.ntnu.idatt2106.sparesti.mapper.AchievementMapper;
import edu.ntnu.idatt2106.sparesti.mapper.BadgeMapper;
import edu.ntnu.idatt2106.sparesti.model.badge.Achievement;
import edu.ntnu.idatt2106.sparesti.model.badge.AchievementCategory;
import edu.ntnu.idatt2106.sparesti.model.badge.Badge;
import edu.ntnu.idatt2106.sparesti.model.user.User;
import edu.ntnu.idatt2106.sparesti.repository.AchievementRepository;
import edu.ntnu.idatt2106.sparesti.repository.BadgeRepository;
import edu.ntnu.idatt2106.sparesti.repository.user.UserRepository;
import java.security.Principal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


/**
 * Service class for operations related to badges.
 *
 * @author Hanne-Sofie Søreide
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class BadgeService {
  private final UserRepository userRepository;
  private final BadgeRepository badgeRepository;
  private final BadgeMapper badgeMapper;
  private final AchievementRepository achievementRepository;
  private final AchievementMapper achievementMapper;

  public BadgePreviewDto getBadgeById(BadgeIdDto badgeIdDto) {
    Badge badge = badgeRepository.findById(badgeIdDto.getId()).orElseThrow();
    return badgeMapper.mapToBadgePreviewDto(badge);
  }

  /**
   * Retrieve a list containing preview DTOs of all the badges belonging to the authenticated user.
   *
   * @param principal The authenticated user
   * @return List of DTOs representing the badges achieved by the user
   */
  public List<BadgePreviewDto> getAllBadgesByEmail(Principal principal, Pageable pageable) {

    String email = principal.getName();

    return badgeRepository.findAllByUser_Email(email, pageable)
            .stream()
            .map(badgeMapper::mapToBadgePreviewDto)
            .toList();
  }

  /**
   * Delete a badge based on its unique id.
   *
   * @param principal  The authenticated user
   * @param badgeIdDto DTO containing the unique badge id
   */
  public void deleteBadgeById(Principal principal, BadgeIdDto badgeIdDto) {
    badgeRepository.deleteById(badgeIdDto.getId());
  }


  /**
   * Create a badge and store it in the database.
   *
   * @param badgeCreateDto DTO containing the information needed to create a badge
   * @param principal      The authenticated user
   * @return The response DTO containing the ID of the created badge
   */
  public BadgePreviewDto createBadge(BadgeCreateDto badgeCreateDto,
                                     Principal principal) {
    String email = principal.getName();

    User user = userRepository.findUserByEmailIgnoreCase(email).orElseThrow(() ->
            new UserNotFoundException("User with email " + email + " not found"));

    Badge createdBadge = badgeMapper.mapToBadge(badgeCreateDto, user);

    Badge savedBadge = badgeRepository.save(createdBadge);

    return badgeMapper.mapToBadgePreviewDto(savedBadge);
  }


  /**
   * Return a DTO representing the achievement that the badge belongs to.
   */
  public Achievement getAchievementOfCategory(
          AchievementCategory achievementCategory) {
    return achievementRepository
            .findByCategory(achievementCategory).orElseThrow(() -> new IllegalArgumentException(
            "No achievement with category: " + achievementCategory)
    );
  }

  /**
   * Retrieve a list of all the achievements in the database.
   *
   * @return List of DTOs representing the achievements
   */
  public List<AchievementPreviewDto> getAchievementPreviews() {
    return achievementRepository.findAll()
            .stream()
            .map(achievementMapper::mapToAchievementPreviewDto)
            .toList();
  }

}
