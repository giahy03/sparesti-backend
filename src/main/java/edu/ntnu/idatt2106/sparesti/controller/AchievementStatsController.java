package edu.ntnu.idatt2106.sparesti.controller;


import edu.ntnu.idatt2106.sparesti.dto.achievementStats.CheckForAchievementDto;
import edu.ntnu.idatt2106.sparesti.dto.badge.BadgePreviewDto;
import edu.ntnu.idatt2106.sparesti.model.badge.Badge;
import edu.ntnu.idatt2106.sparesti.service.achievementStats.AchievementStatsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

/**
 * Controller class for handling requests related to achievement stats of the user.
 *
 * @author Hanne-Sofie Søreide
 */

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/achievement-manager")
@CrossOrigin(origins = "http://localhost:8082")
public class AchievementStatsController {

    private final AchievementStatsService achievementStatsService;


    // PUT/POST category -> Check if category fulfilled.

    @PutMapping("/stat")
    public ResponseEntity<BadgePreviewDto> checkForAchievement(Principal principal, @RequestBody CheckForAchievementDto checkForAchievementDto) {

        int level = achievementStatsService.checkAchievement(checkForAchievementDto, principal);

        if (level > 0) {
            BadgePreviewDto createdBadge = achievementStatsService.createBadge(checkForAchievementDto, principal, level);
            return new ResponseEntity<>(createdBadge, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }



}
