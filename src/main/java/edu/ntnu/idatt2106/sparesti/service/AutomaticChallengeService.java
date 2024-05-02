package edu.ntnu.idatt2106.sparesti.service;

import edu.ntnu.idatt2106.sparesti.dto.challenge.ChallengeRecommendationDto;
import edu.ntnu.idatt2106.sparesti.model.analysis.AnalysisItem;
import edu.ntnu.idatt2106.sparesti.model.analysis.BankStatementAnalysis;
import edu.ntnu.idatt2106.sparesti.model.analysis.ssb.SsbPurchaseCategory;
import edu.ntnu.idatt2106.sparesti.model.banking.BankStatement;
import edu.ntnu.idatt2106.sparesti.model.user.User;
import edu.ntnu.idatt2106.sparesti.repository.user.UserRepository;
import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import lombok.NonNull;
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

  /**
   * Get challenge recommendations for the user.
   *
   * @param principal the user to get recommendations for.
   * @return a list of challenge recommendations.
   */
  public List<ChallengeRecommendationDto> getChallengeRecommendationsForUser(Principal principal) {

    User user = userRepository.findUserByEmailIgnoreCase(principal.getName())
        .orElseThrow(() -> new IllegalArgumentException("User not found"));
    List<BankStatementAnalysis> analyses = user.getBankStatements().stream()
        .sorted(Comparator.comparing(BankStatement::getTimestamp).reversed()).limit(3)
        .map(BankStatement::getAnalysis).filter(Objects::nonNull).toList();

    if (analyses.isEmpty()) {
      throw new IllegalStateException("No analyses found for user");
    }

    BankStatementAnalysis mergedAnalysis = createMergedAnalysis(analyses);

    List<Pair<SsbPurchaseCategory, Double>> recommendations =
        higherThanExpectedAndSortedAfterDifference(mergedAnalysis);


    List<ChallengeRecommendationDto> recommendationDtoObjects = new ArrayList<>();
    for (Pair<SsbPurchaseCategory, Double> recommendation : recommendations) {
      ChallengeRecommendationDto challengeRecommendationDto =
          new ChallengeRecommendationDto("This has no description", LocalDate.now(),
              getRandomDateFromToday(), recommendation.getSecond(),
              recommendation.getFirst().toString());

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
    return LocalDate.now().plusDays(new Random().nextInt(MAX_DAYS - MINIMUM_DAYS) + MINIMUM_DAYS);
  }

  /**
   * Creates a merged analysis from a list of analyses.
   *
   * @param bankStatementAnalyses the analyses to merge
   * @return a merged analysis
   */
  private BankStatementAnalysis createMergedAnalysis(
      List<BankStatementAnalysis> bankStatementAnalyses) {
    List<AnalysisItem> totalItems =
        bankStatementAnalyses.stream().map(BankStatementAnalysis::getAnalysisItems)
            .flatMap(List::stream).toList();


    HashMap<SsbPurchaseCategory, AnalysisItem> mergedItems = new HashMap<>();
    for (SsbPurchaseCategory category : SsbPurchaseCategory.values()) {
      mergedItems.put(category, new AnalysisItem(category, 0.0, 0.0));
    }

    for (AnalysisItem item : totalItems) {
      SsbPurchaseCategory category = item.getPurchaseCategory();
      AnalysisItem mergedItem = mergedItems.get(category);
      mergedItem.setActualValue(mergedItem.getActualValue() + item.getActualValue());
      mergedItem.setExpectedValue(mergedItem.getExpectedValue() + item.getExpectedValue());
    }

    return new BankStatementAnalysis(new ArrayList<>(mergedItems.values()));
  }


}
