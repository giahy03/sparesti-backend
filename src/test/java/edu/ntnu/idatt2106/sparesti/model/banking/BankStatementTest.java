package edu.ntnu.idatt2106.sparesti.model.banking;

import edu.ntnu.idatt2106.sparesti.model.challenge.util.ChallengeUtility;
import edu.ntnu.idatt2106.sparesti.model.user.User;
import java.time.YearMonth;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
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

  @Test
  void BankStatement_RequiredArgsConstructor() {
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

  @Test
  void BankStatement_Access_ReturnsUser() {
    //Arrange
    String expectedUserEmail = user.getEmail();
    String expectedAccountNumber = "123456789";
    String expectedTimestamp = "2020-12";
    int expectedSize = 1;

    // Assert
    assertBankStatementFields(expectedUserEmail, expectedAccountNumber, expectedSize, expectedTimestamp);
  }


  @Test
  void BankStatement_Setter_ReturnCorrectValues() {
    //Arrange
    Transaction transaction = BankingUtility.createTransactionA();
    User expectedUser = ChallengeUtility.createUserB();
    String expectedUserEmail = ChallengeUtility.createUserB().getEmail();
    String expectedAccountNumber = "1234";
    int expectedSize = 2;
    String expectedDate = "2023-12";

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