package edu.ntnu.idatt2106.sparesti.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import edu.ntnu.idatt2106.sparesti.model.challenge.SharedChallenge;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SharedChallengeRepository extends JpaRepository<SharedChallenge, Long> {
  List<SharedChallenge> findSharedChallengeBySharedChallengeCode_Id(String code);
  List<SharedChallenge> findSharedChallengeBySharedChallengeCode_JoinCode(String code);
}