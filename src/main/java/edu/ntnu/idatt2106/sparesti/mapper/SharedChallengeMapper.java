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
 * @author Jeffrey Yaw Annor Tabiri
 * @version 1.0
 * @see ChallengeMapper
 * @see SharedChallenge
 */
@Mapper(uses = ChallengeMapper.class)
public interface SharedChallengeMapper {

  /**
   * Maps a SharedChallengeDto object to a SharedChallenge object.
   *
   * @param challenge The SharedChallengeDto object to map.
   * @param challengeMapperImpl The ChallengeMapper object to map.
   * @return A SharedChallenge object.
   */
  @Mapping(target = "user", ignore = true)
  SharedChallenge sharedChallengeDtoToSharedChallenge(SharedChallengeDto challenge,
                                                      @Context ChallengeMapper challengeMapperImpl);

  /**
   * Maps a SharedChallenge object to a SharedChallengeDto object.
   *
   * @param sharedChallenge The SharedChallenge object to map.
   * @param challengeMapperImpl The ChallengeMapper object to map.
   * @return A SharedChallengeDto object.
   */
  @Mapping(target = "sharedChallengeId", source = "sharedChallengeCode.id")
  SharedChallengeDto sharedChallengeToSharedChallengeDto(SharedChallenge sharedChallenge,
                                                         @Context
                                                         ChallengeMapper challengeMapperImpl);
}
