package edu.ntnu.idatt2106.sparesti.service.analysis;

import edu.ntnu.idatt2106.sparesti.model.analysis.AnalysisItem;
import edu.ntnu.idatt2106.sparesti.model.analysis.BankStatementAnalysis;
import edu.ntnu.idatt2106.sparesti.model.analysis.SsbIncomeQuartile;
import edu.ntnu.idatt2106.sparesti.model.analysis.SsbLivingStatus;
import edu.ntnu.idatt2106.sparesti.model.analysis.SsbPurchaseCategory;
import edu.ntnu.idatt2106.sparesti.model.banking.BankStatement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Analyzes a bank statement and compares it to the expected usage of the given demography.
 */
@Service
@RequiredArgsConstructor
public class BankStatementAnalysisService {
  @NonNull SsbDataService ssbDataService;
  @NonNull TransactionService transactionService;

  /**
   * Analyzes a bank statement and compares it to the expected usage of the given demography.
   *
   * @param bankStatement           The bank statement to analyze.
   * @param monthlyIncomeAfterTaxes The monthly income after taxes.
   * @param livingStatus            The living status.
   * @param incomeQuartile          The income quartile.
   * @return The analysis of the bank statement.
   */
  public BankStatementAnalysis analyze(BankStatement bankStatement, double monthlyIncomeAfterTaxes,
                                       SsbLivingStatus livingStatus,
                                       SsbIncomeQuartile incomeQuartile) {

    categorizeTransactions(bankStatement);
    HashMap<SsbPurchaseCategory, Double> expectedUsage =
        ssbDataService.getExpectedUsage(monthlyIncomeAfterTaxes, livingStatus, incomeQuartile);

    HashMap<SsbPurchaseCategory, Double> actualUsage = getActualUsage(bankStatement);

    List<AnalysisItem> analysisItems = new ArrayList<>();
    Arrays.stream(SsbPurchaseCategory.values()).forEach(category -> {
      AnalysisItem analysisItem = new AnalysisItem(category,
          expectedUsage.get(category) != null ? expectedUsage.get(category) : 0,
          actualUsage.get(category) != null ? actualUsage.get(category) : 0);
      analysisItems.add(analysisItem);
    });

    return new BankStatementAnalysis(analysisItems);
  }

  /**
   * Categorizes the transactions in the bank statement.
   *
   * @param bankStatement The bank statement to categorize.
   */
  private void categorizeTransactions(BankStatement bankStatement) {
    bankStatement.getTransactions()
        .forEach(transaction -> transactionService.categorize(transaction));
  }

  /**
   * Returns the actual usage of the bank statement, per category.
   *
   * @param bankStatement The bank statement to analyze.
   * @return The actual usage of the bank statement, per category.
   */
  protected HashMap<SsbPurchaseCategory, Double> getActualUsage(BankStatement bankStatement) {
    HashMap<SsbPurchaseCategory, Double> actualUsage = new HashMap<>();
    Arrays.stream(SsbPurchaseCategory.values()).forEach(category -> actualUsage.put(category, 0.0));

    bankStatement.getTransactions().forEach(transaction -> {
      SsbPurchaseCategory category = transaction.getCategory();
      actualUsage.put(category, actualUsage.get(category) + transaction.getAmount());
    });
    return actualUsage;
  }
}
