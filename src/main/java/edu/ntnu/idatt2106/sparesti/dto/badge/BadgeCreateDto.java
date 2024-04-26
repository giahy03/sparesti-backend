package edu.ntnu.idatt2106.sparesti.dto.badge;

import edu.ntnu.idatt2106.sparesti.model.badge.Achievement;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class BadgeCreateDto {
    private Achievement achievement;
    private LocalDate achievementDate;
    private int level;
}
