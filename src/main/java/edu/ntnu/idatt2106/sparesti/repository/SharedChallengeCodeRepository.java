package edu.ntnu.idatt2106.sparesti.repository;

import edu.ntnu.idatt2106.sparesti.model.challenge.SharedChallengeCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SharedChallengeCodeRepository extends JpaRepository<SharedChallengeCode, Long> {
}