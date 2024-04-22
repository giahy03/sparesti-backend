package edu.ntnu.idatt2106.sparesti.mapper;

import edu.ntnu.idatt2106.sparesti.dto.badge.BadgePreviewDto;
import edu.ntnu.idatt2106.sparesti.model.badge.Badge;
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

    public BadgePreviewDto mapToBadgePreviewDto(Badge badge) {
        return BadgePreviewDto.builder()
                .badgeId(badge.getBadgeId())
                .achievement(badge.getAchievement().getCategory())
                .threshold(badge.getLevel())
                .level(badge.getLevel())
                .achievementDate(badge.getAchievedDate())
                .build();
    }


}
