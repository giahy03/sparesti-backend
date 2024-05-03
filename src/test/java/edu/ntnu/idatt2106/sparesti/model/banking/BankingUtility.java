package edu.ntnu.idatt2106.sparesti.model.banking;

import edu.ntnu.idatt2106.sparesti.model.analysis.ssb.SsbPurchaseCategory;
import java.time.MonthDay;
import java.time.YearMonth;

/**
 * Utility class for creating bank related objects for testing.
 * This class is used to create objects that are used in the tests
 * for the bank related classes.
 *
 * @version 1.0
 * @see BankStatement
 * @see Transaction
 * @author Jeffrey Yaw Annor Tabiri
 */
public class BankingUtility {

  /**
   * Creates a bank statement object.
   *
   * @return a bank statement object
   */
  public static BankStatement createBankStatementA() {
    BankStatement bankStatement = new BankStatement();
    bankStatement.setAccountNumber("123456789");
    bankStatement.setTimestamp(YearMonth.of(2020, 12));

    return bankStatement;
  }

  /**
   * Creates a transaction object for tests.
   *
   * @return a transaction object.
   */
  public static Transaction createTransactionA() {
    Transaction transaction = new Transaction();
    transaction.setAmount(167.0);
    transaction.setDescription("Test transaction A");
    transaction.setIsIncoming(false);
    transaction.setDate(MonthDay.of(12, 20));
    transaction.setCategory(SsbPurchaseCategory.FOOD);
    return transaction;
  }

}