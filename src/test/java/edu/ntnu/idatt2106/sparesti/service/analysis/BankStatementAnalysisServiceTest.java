package edu.ntnu.idatt2106.sparesti.service.analysis;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import edu.ntnu.idatt2106.sparesti.model.analysis.AnalysisItem;
import edu.ntnu.idatt2106.sparesti.model.analysis.BankStatementAnalysis;
import edu.ntnu.idatt2106.sparesti.model.analysis.SsbIncomeQuartile;
import edu.ntnu.idatt2106.sparesti.model.analysis.SsbLivingStatus;
import edu.ntnu.idatt2106.sparesti.model.analysis.SsbPurchaseCategory;
import edu.ntnu.idatt2106.sparesti.model.banking.BankStatement;
import edu.ntnu.idatt2106.sparesti.model.banking.Transaction;
import edu.ntnu.idatt2106.sparesti.model.user.User;
import java.time.MonthDay;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class BankStatementAnalysisServiceTest {
  @InjectMocks
  private BankStatementAnalysisService bankStatementAnalysisService;
  @Mock
  private SsbDataService ssbDataService;
  //used internally by mockito
  @Mock
  @SuppressWarnings("unused")
  private TransactionService transactionService;

  @Test
  void givenValidBankStatement_whenCalculateActualUsage_thenCostIs100ForFood0ForOther() {

    //arrange
    User testUser = new User();

    HashMap<SsbPurchaseCategory, Double> testExpectedUsage = new HashMap<>();

    for (SsbPurchaseCategory category : SsbPurchaseCategory.values()) {
      testExpectedUsage.put(category, 0.0);
    }
    testExpectedUsage.put(SsbPurchaseCategory.FOOD, 100.0);

    Transaction transaction = new Transaction(MonthDay.now(), "transaction", 100.0, false);
    transaction.setCategory(SsbPurchaseCategory.FOOD);
    List<Transaction> transactions = List.of(transaction);

    BankStatement bankStatement =
        new BankStatement(testUser, "11112233333", transactions, YearMonth.now());

    when(ssbDataService.getExpectedUsage(0.0, SsbLivingStatus.LIVING_ALONE,
        SsbIncomeQuartile.QUARTILE_1)).thenReturn(testExpectedUsage);

    //act

    BankStatementAnalysis bankStatementAnalysis =
        bankStatementAnalysisService.analyze(bankStatement, 0.0, SsbLivingStatus.LIVING_ALONE,
            SsbIncomeQuartile.QUARTILE_1);

    //assert

    for (AnalysisItem analysisItem : bankStatementAnalysis.getAnalysisItems()) {
      if (analysisItem.getPurchaseCategory().equals(SsbPurchaseCategory.FOOD)) {
        assertEquals(analysisItem.getExpectedValue(), 100.0);
      } else {
        assertEquals(analysisItem.getExpectedValue(), 0.0);
      }
    }
  }

  @Test
  void givenValidBankStatement_whenCalculateActualUsage_thenEveryCategoryHasValue100() {
    //arrange
    User testUser = new User();

    Transaction transaction = new Transaction(MonthDay.now(), "transaction", 100.0, false);
    transaction.setCategory(SsbPurchaseCategory.FOOD);
    List<Transaction> transactions = List.of(transaction);

    BankStatement bankStatement =
        new BankStatement(testUser, "11112233333", transactions, YearMonth.now());

    //act
    HashMap<SsbPurchaseCategory, Double> actualUsage =
        bankStatementAnalysisService.getActualUsage(bankStatement);


    //assert
    assertEquals(actualUsage.size(), SsbPurchaseCategory.values().length);
    for (SsbPurchaseCategory category : SsbPurchaseCategory.values()) {
      if (category.equals(SsbPurchaseCategory.FOOD)) {
        assertEquals(actualUsage.get(category), 100.0);
      } else {
        assertEquals(actualUsage.get(category), 0.0);
      }
    }
  }


}
