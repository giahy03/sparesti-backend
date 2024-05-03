package edu.ntnu.idatt2106.sparesti.service.analysis;

import edu.ntnu.idatt2106.sparesti.exception.analysis.ExternalApiException;
import edu.ntnu.idatt2106.sparesti.model.analysis.AnalysisItem;
import edu.ntnu.idatt2106.sparesti.model.analysis.BankStatementAnalysis;
import edu.ntnu.idatt2106.sparesti.model.analysis.ssb.SsbPurchaseCategory;
import edu.ntnu.idatt2106.sparesti.model.banking.BankStatement;
import edu.ntnu.idatt2106.sparesti.model.user.UserInfo;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Analyzes a bank statement and compares it to the expected usage of the given demography.
 *
 * @author Tobias Oftedal
 * @version 1.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class BankStatementAnalysisService {

  private final SsbDataService ssbDataService;
  private final TransactionService transactionService;

  /**
   * Analyzes a bank statement and compares it to the expected usage of the given demography.
   *
   * @param bankStatement The bank statement to analyze.
   * @param userInfo      The user information to compare the bank statement to.
   * @return The analysis of the bank statement.
   */
  public BankStatementAnalysis analyze(@NonNull BankStatement bankStatement,
                                       @NonNull UserInfo userInfo,
                                       boolean categorize
  )
      throws ExternalApiException, NullPointerException {
    HashMap<SsbPurchaseCategory, Double> expectedUsage;
    try {
      expectedUsage =
          ssbDataService.getExpectedUsage(userInfo);
    } catch (Exception e) {
      log.error("Error while fetching expected usage from SSB.", e);
      throw new ExternalApiException("Error while fetching expected usage from SSB.");
    }

    try {
      if (categorize) {
        log.info("Categorizing transactions in bank statement.");
        categorizeTransactions(bankStatement);
      } else {
        categorizeToOtherIfNotSet(bankStatement);
      }
    } catch (Exception e) {
      throw new ExternalApiException("There was an error categorizing transactions using openai.");
    }


    EnumMap<SsbPurchaseCategory, Double> actualUsage = getActualUsage(bankStatement);

    List<AnalysisItem> analysisItems = new ArrayList<>();
    Arrays.stream(SsbPurchaseCategory.values()).forEach(category -> {
      AnalysisItem analysisItem = new AnalysisItem(category,
          expectedUsage.get(category) != null ? expectedUsage.get(category) : 0,
          actualUsage.get(category) != null ? actualUsage.get(category) : 0);
      analysisItems.add(analysisItem);
    });

    BankStatementAnalysis bankStatementAnalysis = new BankStatementAnalysis(analysisItems);

    bankStatementAnalysis.getAnalysisItems().forEach(
        analysisItem -> analysisItem.setBankStatementAnalysis(bankStatementAnalysis));


    return bankStatementAnalysis;


  }

  /**
   * Categorizes the transactions in the bank statement.
   *
   * @param bankStatement The bank statement to categorize.
   */
  private void categorizeTransactions(@NonNull BankStatement bankStatement)
      throws NullPointerException,
      SocketTimeoutException, IndexOutOfBoundsException {
    transactionService.categorizeTransactions(
        bankStatement.getTransactions()
    );
  }

  /**
   * Categorizes the transactions in the
   * bank statement to "OTHER" if they are not already set.
   *
   * @param bankStatement The bank statement to categorize.
   */
  private void categorizeToOtherIfNotSet(@NonNull BankStatement bankStatement) {
    bankStatement.getTransactions().forEach(transaction -> {
      if (transaction.getCategory() == null) {
        transaction.setCategory(SsbPurchaseCategory.OTHER);
      }
    });
  }

  /**
   * Returns the actual usage of the bank statement, per category.
   *
   * @param bankStatement The bank statement to analyze.
   * @return The actual usage of the bank statement, per category.
   */
  protected EnumMap<SsbPurchaseCategory, Double> getActualUsage(
      @NonNull BankStatement bankStatement)
      throws NullPointerException {
    EnumMap<SsbPurchaseCategory, Double> actualUsage = new EnumMap<>(SsbPurchaseCategory.class);
    Arrays.stream(SsbPurchaseCategory.values()).forEach(category -> actualUsage.put(category, 0.0));

    bankStatement.getTransactions()
        .stream()
        .filter(transaction -> !transaction.getIsIncoming())
        .forEach(transaction -> {
          SsbPurchaseCategory category = transaction.getCategory();
          actualUsage.put(category, actualUsage.get(category) + transaction.getAmount());
        });
    return actualUsage;
  }
}
