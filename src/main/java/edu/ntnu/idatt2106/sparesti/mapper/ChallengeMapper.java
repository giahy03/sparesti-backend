package edu.ntnu.idatt2106.sparesti.mapper;

import edu.ntnu.idatt2106.sparesti.dto.challenge.ChallengeDto;
import edu.ntnu.idatt2106.sparesti.dto.challenge.ChallengePreviewDto;
import edu.ntnu.idatt2106.sparesti.model.challenge.Challenge;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * A mapper for mapping between Challenge and ChallengeDto.
 * The mapper is used to map between the two classes.
 *
 * @author Jeffrey Yaw Annor Tabiri
 * @version 1.0
 * @see Challenge
 */
@Mapper
public interface ChallengeMapper {
  @Mapping(target = "user", ignore = true)
  Challenge challengeDtoToChallenge(ChallengeDto challengeDto);

  /**
   * Maps a Challenge object to a ChallengePreviewDto object.
   *
   * @param challenge The Challenge object to map.
   * @return A ChallengePreviewDto object.
   */
  ChallengePreviewDto challengeIntoChallengePreviewDto(Challenge challenge);

  /**
   * Maps a Challenge object to a ChallengeDto object.
   *
   * @param challenge The Challenge object to map.
   * @return A ChallengeDto object.
   */
  ChallengeDto challengeIntoChallengeDto(Challenge challenge);

}
