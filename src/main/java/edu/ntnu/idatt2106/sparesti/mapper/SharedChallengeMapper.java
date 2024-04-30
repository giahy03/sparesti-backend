package edu.ntnu.idatt2106.sparesti.mapper;

import edu.ntnu.idatt2106.sparesti.dto.challenge.SharedChallengeDto;
import edu.ntnu.idatt2106.sparesti.model.challenge.SharedChallenge;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * A mapper for mapping between SharedChallenge and SharedChallengeDto.
 * The mapper is used to map between the two classes.
 *
 * @see ChallengeMapper
 * @see SharedChallenge
 * @author Jeffrey Yaw Annor Tabiri
 * @version 1.0
 */
@Mapper(uses = ChallengeMapper.class)
public interface SharedChallengeMapper {

  @Mapping(target = "user", ignore = true)
  SharedChallenge sharedChallengeDtoToSharedChallenge(SharedChallengeDto challenge,
                                                      @Context ChallengeMapper challengeMapperImpl);

  @Mapping(target = "sharedChallengeId", source = "sharedChallengeCode.id")
  SharedChallengeDto sharedChallengeToSharedChallengeDto(SharedChallenge sharedChallenge,
                                                         @Context ChallengeMapper challengeMapperImpl);
}
