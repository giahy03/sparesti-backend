package edu.ntnu.idatt2106.sparesti.mapper;

import edu.ntnu.idatt2106.sparesti.dto.challenge.ChallengeDto;
import edu.ntnu.idatt2106.sparesti.dto.challenge.SavingChallengeDto;
import edu.ntnu.idatt2106.sparesti.mapper.ChallengeMapper;
import edu.ntnu.idatt2106.sparesti.model.challenge.SavingChallenge;
import edu.ntnu.idatt2106.sparesti.model.user.User;
import org.mapstruct.Context;
import org.mapstruct.Mapper;

/**
 * A mapper for mapping between SavingChallenge and SavingChallengeDto.
 * The mapper is used to map between the two classes.
 *
 * @see ChallengeMapper
 * @see SavingChallenge
 * @author Jeffrey Yaw Annor Tabiri
 * @version 1.0
 */
@Mapper(uses = ChallengeMapper.class)
public interface SavingChallengeMapper{
  SavingChallenge savingChallengeDtoToSavingChallenge(SavingChallengeDto challenge, @Context User user, @Context ChallengeMapper challengeMapperImpl);

  SavingChallengeDto savingChallengeDto(SavingChallenge savingChallenge, @Context ChallengeMapper challengeMapperImpl);
}
