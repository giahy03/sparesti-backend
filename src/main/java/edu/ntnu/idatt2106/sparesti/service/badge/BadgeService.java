package edu.ntnu.idatt2106.sparesti.service.badge;

import edu.ntnu.idatt2106.sparesti.dto.badge.BadgeIdDto;
import edu.ntnu.idatt2106.sparesti.dto.badge.BadgePreviewDto;
import edu.ntnu.idatt2106.sparesti.dto.saving.SavingGoalIdDto;
import edu.ntnu.idatt2106.sparesti.mapper.BadgeMapper;
import edu.ntnu.idatt2106.sparesti.model.badge.Badge;
import edu.ntnu.idatt2106.sparesti.repositories.user.UserRepository;
import edu.ntnu.idatt2106.sparesti.repository.BadgeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

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

    public BadgePreviewDto getBadgeById(BadgeIdDto badgeIdDto) {
        Badge badge = badgeRepository.findById(badgeIdDto.getId()).orElseThrow();
        return badgeMapper.mapToBadgePreviewDto(badge);
    }

    /**
     * Retrieve a list containing the ID number of all the goals belonging to the authenticated user.
     *
     * @param principal The authenticated user
     * @return List of DTOs representing the badges achieved by the user
     */
    public List<BadgePreviewDto> getAllBadgesByEmail(Principal principal, Pageable pageable) {

        String email = principal.getName();

        return badgeRepository.findAllByUser_Username(email, pageable)
                .stream()
                .map(badgeMapper::mapToBadgePreviewDto)
                .toList();
    }

    /**
     * Delete a badge based on its unique id.
     *
     * @param principal The authenticated user
     * @param badgeIdDto DTO containing the unique badge id
     */
    public void deleteBadge(Principal principal, BadgeIdDto badgeIdDto) {
        badgeRepository.deleteById(badgeIdDto.getId());
    }


}
