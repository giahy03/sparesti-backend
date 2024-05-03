package edu.ntnu.idatt2106.sparesti.service.badge;

import edu.ntnu.idatt2106.sparesti.dto.achievementstats.AchievementPreviewDto;
import edu.ntnu.idatt2106.sparesti.dto.badge.BadgePreviewDto;
import edu.ntnu.idatt2106.sparesti.mapper.AchievementMapper;
import edu.ntnu.idatt2106.sparesti.mapper.BadgeMapper;
import edu.ntnu.idatt2106.sparesti.repository.AchievementRepository;
import edu.ntnu.idatt2106.sparesti.repository.BadgeRepository;
import java.security.Principal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


/**
 * Service class for operations related to badges.
 * A user can retrieve all its achieved badges and
 * possible badges in Sparesti.
 *
 * @author Hanne-Sofie Søreide
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class BadgeService {
  private final BadgeRepository badgeRepository;
  private final BadgeMapper badgeMapper;
  private final AchievementRepository achievementRepository;
  private final AchievementMapper achievementMapper;

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
