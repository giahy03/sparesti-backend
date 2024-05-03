package edu.ntnu.idatt2106.sparesti.model.banking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import edu.ntnu.idatt2106.sparesti.model.analysis.ssb.SsbPurchaseCategory;
import java.time.MonthDay;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


/**
 * Test class for a transaction.
 *
 * @version 1.0
 * @see Transaction
 * @author Jeffrey Yaw Annor Tabiri
 */
class TransactionTest {

  Transaction transaction;

  @BeforeEach
  void setUp() {
    transaction = BankingUtility.createTransactionA();
  }

  @DisplayName("Test that transaction constructor returns a transaction object")
  @Test
  void transaction_RequiredArgsConstructor_ReturnsNotNull() {
    //Arrange
    MonthDay expectedMonthDay = MonthDay.of(12, 20);
    String expectedDescription = "Test transaction A";
    double expectedAmount = 167.0;
    boolean expectedIsIncoming = false;
    Transaction transaction = new Transaction(
            expectedMonthDay, expectedDescription, expectedAmount, expectedIsIncoming);

    // Act
    MonthDay actualDate = transaction.getDate();
    String actualDescription = transaction.getDescription();
    double actualAmount = transaction.getAmount();
    boolean actualIsIncoming = transaction.getIsIncoming();

    // Assert
    assertTransactionFields(expectedMonthDay, actualDate, expectedDescription, actualDescription,
            expectedAmount, actualAmount, expectedIsIncoming, actualIsIncoming);

  }


  @DisplayName("Test that transaction access returns correct fields")
  @Test
  void transaction_Access_ReturnsFields() {
    //Arrange
    MonthDay expectedMonthDay = MonthDay.of(12, 20);
    String expectedDescription = "Test transaction A";
    double expectedAmount = 167.0;
    boolean expectedIsIncoming = false;
    SsbPurchaseCategory expectedCategory = SsbPurchaseCategory.FOOD;

    // Act
    MonthDay actual = transaction.getDate();
    String actualDescription = transaction.getDescription();
    double actualAmount = transaction.getAmount();
    boolean actualIsIncoming = transaction.getIsIncoming();
    SsbPurchaseCategory actualCategory = transaction.getCategory();


    // Assert
    assertTransactionFields(expectedMonthDay, actual, expectedDescription, actualDescription,
            expectedAmount, actualAmount, expectedIsIncoming, actualIsIncoming);
    assertEquals(expectedCategory, actualCategory);
  }


  @DisplayName("Test that transaction setters return correct field")
  @Test
  void transaction_Setters_ReturnsCorrectField() {
    //Arrange
    MonthDay expectedMonthDay = MonthDay.of(12, 10);
    String expectedDescription = "Test transaction B";
    double expectedAmount = 190.0;
    boolean expectedIsIncoming = false;
    SsbPurchaseCategory expectedCategory = SsbPurchaseCategory.ALCOHOL_AND_TOBACCO;

    // Act
    transaction.setDate(expectedMonthDay);
    transaction.setDescription(expectedDescription);
    transaction.setAmount(expectedAmount);
    transaction.setIsIncoming(expectedIsIncoming);
    transaction.setCategory(expectedCategory);

    MonthDay actual = transaction.getDate();
    String actualDescription = transaction.getDescription();
    double actualAmount = transaction.getAmount();
    boolean actualIsIncoming = transaction.getIsIncoming();
    SsbPurchaseCategory actualCategory = transaction.getCategory();

    // Assert
    assertTransactionFields(expectedMonthDay, actual,
            expectedDescription, actualDescription, expectedAmount,
            actualAmount, expectedIsIncoming, actualIsIncoming);
    assertEquals(expectedCategory, actualCategory);
  }


  @DisplayName("Test that transaction setBankStatement returns correct bank statement")
  @Test
  void transaction_SetBankStatement_ReturnsCorrectBankStatement() {
    //Arrange
    BankStatement expected = BankingUtility.createBankStatementA();

    // Act
    transaction.setBankStatement(expected);

    // Assert
    BankStatement actual = transaction.getBankStatement();
    assertEquals(expected.getAccountNumber(), actual.getAccountNumber());
    assertEquals(expected.getTimestamp(), actual.getTimestamp());
  }

  private static void assertTransactionFields(MonthDay expectedMonthDay,
                                              MonthDay actualDate, String expectedDescription,
                                              String actualDescription,
                                              double expectedAmount, double actualAmount,
                                              boolean expectedIsIncoming,
                                              boolean actualIsIncoming) {
    assertEquals(expectedMonthDay, actualDate);
    assertEquals(expectedDescription, actualDescription);
    assertEquals(expectedAmount, actualAmount);
    assertEquals(expectedIsIncoming, actualIsIncoming);
  }

}