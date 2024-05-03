package edu.ntnu.idatt2106.sparesti.service;

import edu.ntnu.idatt2106.sparesti.dto.challenge.ChallengeRecommendationDto;
import edu.ntnu.idatt2106.sparesti.model.analysis.BankStatementAnalysis;
import edu.ntnu.idatt2106.sparesti.model.analysis.ssb.SsbPurchaseCategory;
import edu.ntnu.idatt2106.sparesti.model.banking.BankStatement;
import edu.ntnu.idatt2106.sparesti.model.user.User;
import edu.ntnu.idatt2106.sparesti.repository.user.UserRepository;
import java.security.Principal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

/**
 * Service for creating random challenge recommendations.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class AutomaticChallengeService {

  private final UserRepository userRepository;
  private static final int MINIMUM_DAYS = 5;
  private static final int MAX_DAYS = 30;
  private static final Random random = new Random();

  /**
   * Get challenge recommendations for the user.
   *
   * @param principal the user to get recommendations for.
   * @return a list of challenge recommendations.
   */
  public List<ChallengeRecommendationDto> getChallengeRecommendationsForUser(Principal principal) {

    User user = userRepository.findUserByEmailIgnoreCase(principal.getName())
        .orElseThrow(() -> new IllegalArgumentException("User not found"));
    BankStatementAnalysis mostRecentAnalysis = user.getBankStatements().stream()
        .max(Comparator.comparing(BankStatement::getTimestamp))
        .map(BankStatement::getAnalysis)
        .orElseThrow();

    List<Pair<SsbPurchaseCategory, Double>> recommendations =
        higherThanExpectedAndSortedAfterDifference(mostRecentAnalysis);


    List<ChallengeRecommendationDto> recommendationDtoObjects = new ArrayList<>();
    for (Pair<SsbPurchaseCategory, Double> recommendation : recommendations) {
      LocalDate today = LocalDate.now();
      LocalDate endDate = getRandomDateFromToday();
      long daysBetween = ChronoUnit.DAYS.between(today, endDate);
      double dailyAmount = (recommendation.getSecond() / daysBetween);

      String description = String.format(
          "Bruk %skr mindre på %s hver dag, over de neste %d dagene",
          (int) dailyAmount, recommendation.getFirst().translateToNorwegian(), daysBetween);

      ChallengeRecommendationDto challengeRecommendationDto =
          new ChallengeRecommendationDto(
              description,
              today,
              endDate,
              dailyAmount,
              recommendation.getFirst().translateToNorwegian());

      recommendationDtoObjects.add(challengeRecommendationDto);
    }

    log.info("Returning challenge recommendations for user: {}",
        principal.getName() + " with size:" + " " + recommendationDtoObjects.size());
    return recommendationDtoObjects;
  }

  /**
   * Returns a list of purchase categories that have a higher usage than expected and sorted after
   * the difference.
   *
   * @param analysis the analysis to get the items from
   * @return a list of purchase categories that have a higher usage than expected and sorted after
   */
  private List<Pair<SsbPurchaseCategory, Double>> higherThanExpectedAndSortedAfterDifference(
      BankStatementAnalysis analysis) {
    return analysis.getAnalysisItems().stream()
        .filter(item -> item.getActualValue() > item.getExpectedValue())
        .sorted(Comparator.comparingDouble(item -> item.getActualValue() - item.getExpectedValue()))
        .map(item -> Pair.of(item.getPurchaseCategory(),
            item.getActualValue() - item.getExpectedValue())).toList();
  }

  /**
   * Creates a random date between today and a number of days in the future.
   *
   * @return a random date between today and a number of days in the future
   */
  private LocalDate getRandomDateFromToday() {
    return LocalDate.now().plusDays(random.nextInt(MAX_DAYS - MINIMUM_DAYS) + (long) MINIMUM_DAYS);
  }
}
