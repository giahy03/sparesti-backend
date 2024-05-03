package edu.ntnu.idatt2106.sparesti.model.banking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import edu.ntnu.idatt2106.sparesti.model.challenge.util.ChallengeUtility;
import edu.ntnu.idatt2106.sparesti.model.user.User;
import java.time.YearMonth;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Test class for a bank statement.
 *
 * @version 1.0
 * @see BankStatement
 * @author Jeffrey Yaw Annor Tabiri
 */
class BankStatementTest {
  BankStatement bankStatementEmail;
  static User user;

  static Transaction transaction;

  @BeforeAll
  static void beforeAll() {
    user = ChallengeUtility.createUserA();
    transaction = BankingUtility.createTransactionA();
  }

  @BeforeEach
  void setUp() {
    bankStatementEmail = BankingUtility.createBankStatementA();
    bankStatementEmail.setUser(user);
    bankStatementEmail.setTransactions(List.of(transaction));
  }

  @DisplayName("Test for the getter methods of BankStatement")
  @Test
  void bankStatement_RequiredArgsConstructor() {
    //Arrange
    BankStatement bankStatement = new BankStatement("123456789",
            List.of(BankingUtility.createTransactionA()), YearMonth.of(2020, 12));
    int expectedSize = 1;
    YearMonth expectedTimestamp = YearMonth.of(2020, 12);

    // Act
    String actualAccountNumber = bankStatement.getAccountNumber();
    int actualSize = bankStatement.getTransactions().size();
    YearMonth actualTimestamp = bankStatement.getTimestamp();

    // Assert
    assertEquals(expectedSize, actualSize);
    assertEquals(expectedTimestamp, actualTimestamp);
    assertEquals("123456789", actualAccountNumber);
  }

  @DisplayName("Test for the getter methods of BankStatement")
  @Test
  void bankStatement_Access_ReturnsUser() {
    //Arrange
    String expectedUserEmail = user.getEmail();
    String expectedAccountNumber = "123456789";
    String expectedTimestamp = "2020-12";
    int expectedSize = 1;

    // Assert
    assertBankStatementFields(expectedUserEmail, expectedAccountNumber,
            expectedSize, expectedTimestamp);
  }


  @DisplayName("Test for the setter methods of BankStatement")
  @Test
  void bankStatement_Setter_ReturnCorrectValues() {
    //Arrange
    final Transaction transaction = BankingUtility.createTransactionA();
    final User expectedUser = ChallengeUtility.createUserB();
    final String expectedUserEmail = ChallengeUtility.createUserB().getEmail();
    final String expectedAccountNumber = "1234";
    final int expectedSize = 2;
    final String expectedDate = "2023-12";

    //Act
    bankStatementEmail.setUser(expectedUser);
    bankStatementEmail.setAccountNumber(expectedAccountNumber);
    bankStatementEmail.setTransactions(List.of(transaction, transaction));
    bankStatementEmail.setTimestamp(YearMonth.of(2023, 12));

    //Assert
    assertBankStatementFields(expectedUserEmail, expectedAccountNumber, expectedSize, expectedDate);
  }

  private void assertBankStatementFields(String expectedUserEmail, String expectedAccountNumber,
                                                int expectedSize, String expectedDate) {
    assertEquals(expectedUserEmail, bankStatementEmail.getUser().getEmail());
    assertEquals(expectedAccountNumber, bankStatementEmail.getAccountNumber());
    assertEquals(expectedSize, bankStatementEmail.getTransactions().size());
    assertEquals(expectedDate, bankStatementEmail.getTimestamp().toString());
  }

}