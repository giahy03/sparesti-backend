package edu.ntnu.idatt2106.sparesti.mapper;

import edu.ntnu.idatt2106.sparesti.dto.challenge.ChallengeDto;
import edu.ntnu.idatt2106.sparesti.dto.challenge.ChallengePreviewDto;
import edu.ntnu.idatt2106.sparesti.model.challenge.Challenge;
import edu.ntnu.idatt2106.sparesti.model.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper
public interface ChallengeMapper {
  @Mapping(target = "user", source = "user")
  Challenge challengeDtoToChallenge(ChallengeDto challengeDto, User user);

  ChallengePreviewDto challengeIntoChallengePreviewDto(Challenge challenge);

  ChallengeDto challengeIntoChallengeDto(Challenge challenge);
}
