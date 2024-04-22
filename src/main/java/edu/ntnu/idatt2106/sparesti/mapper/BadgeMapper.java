package edu.ntnu.idatt2106.sparesti.mapper;

import edu.ntnu.idatt2106.sparesti.dto.badge.BadgePreviewDto;
import edu.ntnu.idatt2106.sparesti.dto.badge.CreateBadgeDto;
import edu.ntnu.idatt2106.sparesti.model.badge.Badge;
import edu.ntnu.idatt2106.sparesti.model.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

/**
 * Mapper class for mapping between Badge objects and Dto objects.
 *
 * @author Hanne-Sofie Søreide
 */
@Component
@RequiredArgsConstructor
public class BadgeMapper {

    public BadgePreviewDto mapToBadgePreviewDto(Badge badge) {
        return BadgePreviewDto.builder()
                .badgeId(badge.getId())
                .achievement(badge.getAchievement().getCategory())
                .threshold(badge.getLevel())
                .level(badge.getLevel())
                .achievementDate(badge.getAchievedDate())
                .build();
    }

    public Badge mapToBadge(CreateBadgeDto createBadgeDto, User user) {

        return Badge.builder()
                .user(user)
                .achievedDate(LocalDate.now())
                .level(createBadgeDto.getLevel())
                .achievement(createBadgeDto.getAchievement())
                .build();
    }

}
