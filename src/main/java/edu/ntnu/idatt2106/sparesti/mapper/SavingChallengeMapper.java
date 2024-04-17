package edu.ntnu.idatt2106.sparesti.mapper;

import edu.ntnu.idatt2106.sparesti.dto.challenge.ChallengeDto;
import edu.ntnu.idatt2106.sparesti.dto.challenge.SavingChallengeDto;
import edu.ntnu.idatt2106.sparesti.mapper.ChallengeMapper;
import edu.ntnu.idatt2106.sparesti.model.challenge.SavingChallenge;
import org.mapstruct.Context;
import org.mapstruct.Mapper;

@Mapper(uses = ChallengeMapper.class)
public interface SavingChallengeMapper{
  SavingChallenge savingChallengeDtoToSavingChallenge(SavingChallengeDto challenge, @Context ChallengeMapper challengeMapperImpl);

  SavingChallengeDto savingChallengeDto(SavingChallenge savingChallenge, @Context ChallengeMapper challengeMapperImpl);
}
