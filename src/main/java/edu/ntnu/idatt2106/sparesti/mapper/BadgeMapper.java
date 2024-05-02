package edu.ntnu.idatt2106.sparesti.mapper;

import edu.ntnu.idatt2106.sparesti.dto.badge.BadgeCreateDto;
import edu.ntnu.idatt2106.sparesti.dto.badge.BadgePreviewDto;
import edu.ntnu.idatt2106.sparesti.model.badge.Badge;
import edu.ntnu.idatt2106.sparesti.model.user.User;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Mapper class for mapping between Badge objects and Dto objects.
 *
 * @author Hanne-Sofie Søreide
 */
@Component
@RequiredArgsConstructor
public class BadgeMapper {

  /**
   * Maps a Badge to a BadgePreviewDto.
   *
   * @param badge the Badge to be mapped
   * @return the mapped BadgePreviewDto
   */
  public BadgePreviewDto mapToBadgePreviewDto(Badge badge) {

    return BadgePreviewDto.builder()
        .achievement(badge.getAchievement().getCategory())
        .threshold(badge.getAchievement().getThresholds().get(badge.getLevel() - 1))
        .achievementDate(badge.getAchievedDate())
        .build();
  }

  /**
   * Maps a BadgeCreateDto to a Badge.
   *
   * @param createBadgeDto the BadgeCreateDto to be mapped
   * @param user           the user that the badge belongs to
   * @return the mapped Badge
   */
  public Badge mapToBadge(BadgeCreateDto createBadgeDto, User user) {
    return Badge.builder()
        .user(user)
        .achievedDate(LocalDate.now())
        .level(createBadgeDto.getLevel())
        .achievement(createBadgeDto.getAchievement())
        .build();
  }

}
