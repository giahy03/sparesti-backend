package edu.ntnu.idatt2106.sparesti.service.analysis;

import edu.ntnu.idatt2106.sparesti.dto.analysis.TransactionDto;
import edu.ntnu.idatt2106.sparesti.exception.auth.UnauthorizedOperationException;
import edu.ntnu.idatt2106.sparesti.exception.user.UserNotFoundException;
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
import lombok.NonNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.security.Principal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Test class for BankStatementService
 *
 *
 * @version 1.0
 * @author Jeffrey Yaw Annor Tabiri
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
    BankStatementAnalysis bankStatementAnalysis = ChallengeUtility.createBankStatementAnalysis1(List.of(analysisItem, analysisItem));
    bankStatement.setAnalysis(bankStatementAnalysis);
    bankStatement.setUser(user);
    user.setBankStatements(List.of(bankStatement));
  }

  @Test
  void Service_SaveBankStatement_SavesBankStatement() {
    //Act
    bankStatementService.saveBankStatement(bankStatement);

    //Assert
    verify(bankStatementRepository, times(1)).save(bankStatement);
  }

  @Test
  void deleteAnalysisItems() {

    AnalysisItem analysisItem = ChallengeUtility.createAnalysisItem1();
    analysisItem.setPurchaseCategory(SsbPurchaseCategory.FOOD);
    BankStatementAnalysis bankStatementAnalysis = ChallengeUtility.createBankStatementAnalysis1(List.of(analysisItem, analysisItem));
    bankStatement.setAnalysis(bankStatementAnalysis);

    bankStatementService.deleteAnalysisItems(bankStatement.getAnalysis());

    verify(analysisItemRepository, times(1)).deleteAnalysisItemByBankStatementAnalysis(bankStatement.getAnalysis());
  }

  @Test
  void Service_readAndSaveBankStatement_ReturnsBankStatement() throws IOException {
      // Arrange
      MultipartFile file = mock(MultipartFile.class);
      Principal principal = mock(Principal.class);
      Bank bank = Bank.DNB; // or any other Bank enum value
      BankStatementReader bankStatementReader = mock(DnbReader.class);

      when(userRepository.findUserByEmailIgnoreCase(principal.getName())).thenReturn(Optional.of(user));
      when(file.getOriginalFilename()).thenReturn("testFile");

      // Act
    assertThrows(RuntimeException.class, () -> bankStatementService.readAndSaveBankStatement(file, principal, bank));
  }

  @Test
  void Service_GetBankStatement_ReturnStatement() {

    when(bankStatementRepository.findByIdAndUser(1L, user)).thenReturn(java.util.Optional.of(bankStatement));
    when(userRepository.findUserByEmailIgnoreCase(principal.getName())).thenReturn(java.util.Optional.of(user));
    //Act
    BankStatement statement = bankStatementService.getBankStatement(1L, principal);

    //Assert
    assertNotNull(statement);
  }

  @Test
  void Service_GetBankStatement_ReturnException() {
    assertThrows(NoSuchElementException.class, () -> bankStatementService.getBankStatement(1L, principal));

    when(userRepository.findUserByEmailIgnoreCase(principal.getName())).thenReturn(java.util.Optional.of(user));
    assertThrows(NoSuchElementException.class, () -> bankStatementService.getBankStatement(1L, principal));

    bankStatement.setUser(new User());
    when(bankStatementRepository.findByIdAndUser(1L, user)).thenReturn(java.util.Optional.of(bankStatement));
    when(userRepository.findUserByEmailIgnoreCase(principal.getName())).thenReturn(java.util.Optional.of(user));
    assertThrows(UnauthorizedOperationException.class, () -> bankStatementService.getBankStatement(1L, principal));
  }





  @Test
  void Service_GetAllBankStatements_ReturnsListOfBankStatement() {
    when(userRepository.findUserByEmailIgnoreCase(principal.getName())).thenReturn(java.util.Optional.of(user));
    when(bankStatementRepository.findAllByUser(user)).thenReturn(List.of(bankStatement));

    //Act
    List<BankStatement> statements = bankStatementService.getAllBankStatements(principal);

    //Assert
    assertNotNull(statements);
  }

  @Test
  void Service_getAllAccountNumbers_ReturnsLisOfAccountNumbers() {
    when(userRepository.findUserByEmailIgnoreCase(principal.getName())).thenReturn(java.util.Optional.of(user));
    when(bankStatementRepository.findAllByUser(user)).thenReturn(List.of(bankStatement));

    //Act
    bankStatementService.getAllAccountNumbers(principal);

    //Assert
    verify(bankStatementRepository, times(1)).findAllByUser(user);
  }

  @Test
  void Service_getTransactions_ReturnListOfTransactions() {
    String accountNumber = "1234";
    int pageNumber = 1;
    int pageSize = 1;
    Transaction transaction = ChallengeUtility.createTransaction1();
    transaction.setCategory(SsbPurchaseCategory.EDUCATION);
    transaction.setBankStatement(bankStatement);
    bankStatement.setTransactions((List.of(transaction, transaction)));
    when(transactionRepository.findByBankStatement_AccountNumberAndBankStatement_User_Email(
            accountNumber, principal.getName(), PageRequest.of(pageNumber, pageSize)))
            .thenReturn(List.of(transaction, transaction));

    //Act
    List<TransactionDto> transactionDtoList =  bankStatementService.getTransactions("1234", principal, 1, 1);

    //Assert
    assertNotNull(transactionDtoList);
  }

  @Test
  void deleteBankStatement() {
    when(userRepository.findUserByEmailIgnoreCase(principal.getName())).thenReturn(java.util.Optional.of(user));
    when(bankStatementRepository.findByIdAndUser(1L, user)).thenReturn(java.util.Optional.of(bankStatement));

    //Act
    bankStatementService.deleteBankStatement(1L, principal);

    //Assert
    verify(bankStatementRepository, times(1)).delete(bankStatement);
  }
}