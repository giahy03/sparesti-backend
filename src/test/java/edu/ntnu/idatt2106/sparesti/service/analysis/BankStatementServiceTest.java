package edu.ntnu.idatt2106.sparesti.service.analysis;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import edu.ntnu.idatt2106.sparesti.dto.analysis.TransactionDto;
import edu.ntnu.idatt2106.sparesti.exception.auth.UnauthorizedOperationException;
import edu.ntnu.idatt2106.sparesti.filehandling.BankStatementReader;
import edu.ntnu.idatt2106.sparesti.filehandling.DnbReader;
import edu.ntnu.idatt2106.sparesti.model.analysis.AnalysisItem;
import edu.ntnu.idatt2106.sparesti.model.analysis.BankStatementAnalysis;
import edu.ntnu.idatt2106.sparesti.model.analysis.ssb.SsbPurchaseCategory;
import edu.ntnu.idatt2106.sparesti.model.banking.Bank;
import edu.ntnu.idatt2106.sparesti.model.banking.BankStatement;
import edu.ntnu.idatt2106.sparesti.model.banking.Transaction;
import edu.ntnu.idatt2106.sparesti.model.challenge.util.ChallengeUtility;
import edu.ntnu.idatt2106.sparesti.model.user.User;
import edu.ntnu.idatt2106.sparesti.repository.AnalysisItemRepository;
import edu.ntnu.idatt2106.sparesti.repository.BankStatementRepository;
import edu.ntnu.idatt2106.sparesti.repository.TransactionRepository;
import edu.ntnu.idatt2106.sparesti.repository.user.UserRepository;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.multipart.MultipartFile;

/**
 * Test class for BankStatementService.
 *
 * @author Jeffrey Yaw Annor Tabiri
 * @version 1.0
 */
@ExtendWith(MockitoExtension.class)
class BankStatementServiceTest {
  @InjectMocks
  BankStatementService bankStatementService;
  @Mock
  UserRepository userRepository;

  @Mock
  BankStatementRepository bankStatementRepository;

  @Mock
  TransactionRepository transactionRepository;

  @Mock
  AnalysisItemRepository analysisItemRepository;

  BankStatement bankStatement;

  Principal principal;

  User user;

  @BeforeEach
  void setUp() {
    bankStatement = ChallengeUtility.createBankStatement1();
    user = ChallengeUtility.createUser1();
    principal = () -> user.getEmail();
    Transaction transaction = ChallengeUtility.createTransaction1();
    transaction.setCategory(SsbPurchaseCategory.EDUCATION);
    transaction.setBankStatement(bankStatement);
    bankStatement.setTransactions((List.of(transaction, transaction)));
    AnalysisItem analysisItem = ChallengeUtility.createAnalysisItem1();
    analysisItem.setPurchaseCategory(SsbPurchaseCategory.FOOD);
    BankStatementAnalysis bankStatementAnalysis =
            ChallengeUtility.createBankStatementAnalysis1(List.of(analysisItem, analysisItem));
    bankStatement.setAnalysis(bankStatementAnalysis);
    bankStatement.setUser(user);
    user.setBankStatements(List.of(bankStatement));
  }

  @DisplayName("Given valid bank statement, when save bank statement, then bank statement is saved")
  @Test
  void service_SaveBankStatement_SavesBankStatement() {
    //Act
    bankStatementService.saveBankStatement(bankStatement);

    //Assert
    verify(bankStatementRepository, times(1)).save(bankStatement);
  }

  @DisplayName("Given valid bank statement, "
          + "when delete analysis items, then analysis items are deleted")
  @Test
  void deleteAnalysisItems() {

    AnalysisItem analysisItem = ChallengeUtility.createAnalysisItem1();
    analysisItem.setPurchaseCategory(SsbPurchaseCategory.FOOD);
    BankStatementAnalysis bankStatementAnalysis =
            ChallengeUtility.createBankStatementAnalysis1(List.of(analysisItem, analysisItem));
    bankStatement.setAnalysis(bankStatementAnalysis);

    bankStatementService.deleteAnalysisItems(bankStatement.getAnalysis());

    verify(analysisItemRepository, times(1))
            .deleteAnalysisItemByBankStatementAnalysis(bankStatement.getAnalysis());
  }

  @DisplayName("Given valid bank statement, "
          + "when delete transactions, then transactions are deleted")
  @Test
  void service_readAndSaveBankStatement_ReturnsBankStatement() throws IOException {
    // Arrange
    MultipartFile file = mock(MultipartFile.class);
    Principal principal = mock(Principal.class);
    Bank bank = Bank.DNB;
    BankStatementReader bankStatementReader = mock(DnbReader.class);

    when(userRepository.findUserByEmailIgnoreCase(
            principal.getName())).thenReturn(Optional.of(user));
    when(file.getOriginalFilename()).thenReturn("testFile");

    // Act
    assertThrows(RuntimeException.class, () ->
            bankStatementService.readAndSaveBankStatement(file, principal, bank));
  }

  @DisplayName("Given valid bank statement, "
          + "when get bank statement, then bank statement is returned")
  @Test
  void service_GetBankStatement_ReturnStatement() {

    when(bankStatementRepository.findByIdAndUser(1L, user))
            .thenReturn(java.util.Optional.of(bankStatement));
    when(userRepository.findUserByEmailIgnoreCase(principal.getName()))
            .thenReturn(java.util.Optional.of(user));
    //Act
    BankStatement statement =
            bankStatementService.getBankStatement(1L, principal);

    //Assert
    assertNotNull(statement);
  }

  @DisplayName("Given invalid bank statement, when get bank statement, then exception is thrown")
  @Test
  void service_GetBankStatement_ReturnException() {
    assertThrows(NoSuchElementException.class, () ->
            bankStatementService.getBankStatement(1L, principal));

    when(userRepository.findUserByEmailIgnoreCase(
            principal.getName())).thenReturn(java.util.Optional.of(user));
    assertThrows(NoSuchElementException.class, () ->
            bankStatementService.getBankStatement(1L, principal));

    bankStatement.setUser(new User());
    when(bankStatementRepository.findByIdAndUser(1L, user))
            .thenReturn(java.util.Optional.of(bankStatement));
    when(userRepository.findUserByEmailIgnoreCase(
            principal.getName())).thenReturn(java.util.Optional.of(user));
    assertThrows(UnauthorizedOperationException.class, () ->
            bankStatementService.getBankStatement(1L, principal));
  }


  @DisplayName("Given valid principal, "
          + "when get all bank statements, then list of bank statements is returned")
  @Test
  void service_GetAllBankStatements_ReturnsListOfBankStatement() {
    when(userRepository.findUserByEmailIgnoreCase(
            principal.getName())).thenReturn(java.util.Optional.of(user));
    when(bankStatementRepository.findAllByUser(user)).thenReturn(List.of(bankStatement));

    //Act
    List<BankStatement> statements = bankStatementService.getAllBankStatements(principal);

    //Assert
    assertNotNull(statements);
  }

  @DisplayName("Given valid principal, "
          + "when get all account numbers, then list of account numbers is returned")
  @Test
  void service_getAllAccountNumbers_ReturnsLisOfAccountNumbers() {
    when(userRepository
            .findUserByEmailIgnoreCase(
                    principal.getName())).thenReturn(java.util.Optional.of(user));
    when(bankStatementRepository.findAllByUser(user)).thenReturn(List.of(bankStatement));

    //Act
    bankStatementService.getAllAccountNumbers(principal);

    //Assert
    verify(bankStatementRepository, times(1)).findAllByUser(user);
  }

  @DisplayName("Given valid principal, "
          + "when get transactions, then list of transactions is returned")
  @Test
  void service_getTransactions_ReturnListOfTransactions() {

    Transaction transaction = ChallengeUtility.createTransaction1();
    transaction.setCategory(SsbPurchaseCategory.EDUCATION);
    transaction.setBankStatement(bankStatement);

    int pageNumber = 1;
    int pageSize = 1;
    String accountNumber = "1234";

    bankStatement.setTransactions((List.of(transaction, transaction)));
    when(transactionRepository.findByBankStatement_AccountNumberAndBankStatement_User_Email(
            accountNumber, principal.getName(), PageRequest.of(pageNumber, pageSize)))
            .thenReturn(List.of(transaction, transaction));

    //Act
    List<TransactionDto> transactionDtoList =
            bankStatementService.getTransactions("1234", principal, 1, 1);

    //Assert
    assertNotNull(transactionDtoList);
  }

  @DisplayName("Given valid principal, when delete bank statement, then bank statement is deleted")
  @Test
  void deleteBankStatement() {
    when(userRepository.findUserByEmailIgnoreCase(
            principal.getName())).thenReturn(java.util.Optional.of(user));
    when(bankStatementRepository.findByIdAndUser(1L, user))
            .thenReturn(java.util.Optional.of(bankStatement));

    //Act
    bankStatementService.deleteBankStatement(1L, principal);

    //Assert
    verify(bankStatementRepository, times(1)).delete(bankStatement);
  }

}