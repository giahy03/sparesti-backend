package edu.ntnu.idatt2106.sparesti.mapper;

import edu.ntnu.idatt2106.sparesti.dto.challenge.ChallengeDto;
import edu.ntnu.idatt2106.sparesti.dto.challenge.ChallengePreviewDto;
import edu.ntnu.idatt2106.sparesti.model.challenge.Challenge;
import edu.ntnu.idatt2106.sparesti.model.user.User;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * A mapper for mapping between Challenge and ChallengeDto.
 * The mapper is used to map between the two classes.
 *
 * @see Challenge
 * @author Jeffrey Yaw Annor Tabiri
 * @version 1.0
 */
@Mapper
public interface ChallengeMapper {

  @Mapping(target = "isCompleted", ignore = true)
  @Mapping(target = "user", ignore = true)
  Challenge challengeDtoToChallenge(ChallengeDto challengeDto);

  ChallengePreviewDto challengeIntoChallengePreviewDto(Challenge challenge);

  ChallengeDto challengeIntoChallengeDto(Challenge challenge);
}
