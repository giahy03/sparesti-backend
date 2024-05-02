package edu.ntnu.idatt2106.sparesti.service;

import edu.ntnu.idatt2106.sparesti.dto.challenge.ChallengeRecommendationDto;
import edu.ntnu.idatt2106.sparesti.model.analysis.AnalysisItem;
import edu.ntnu.idatt2106.sparesti.model.analysis.BankStatementAnalysis;
import edu.ntnu.idatt2106.sparesti.model.analysis.ssb.SsbPurchaseCategory;
import edu.ntnu.idatt2106.sparesti.model.banking.BankStatement;
import edu.ntnu.idatt2106.sparesti.model.banking.Transaction;
import edu.ntnu.idatt2106.sparesti.model.challenge.util.ChallengeUtility;
import edu.ntnu.idatt2106.sparesti.model.user.User;
import edu.ntnu.idatt2106.sparesti.repository.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.security.Principal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AutomaticChallengeServiceTest {

  @InjectMocks
  AutomaticChallengeService automaticChallengeService;

  @Mock
  UserRepository userRepository;

  Principal principal;

  User user;

  @BeforeEach
  void setUp() {
    user = ChallengeUtility.createUser1();
    BankStatement bankStatement = ChallengeUtility.createBankStatement1();
    Transaction transaction = ChallengeUtility.createTransaction1();
    transaction.setCategory(SsbPurchaseCategory.EDUCATION);
    transaction.setBankStatement(bankStatement);
    bankStatement.setTransactions((List.of(transaction, transaction, transaction, transaction, transaction)));
    AnalysisItem analysisItem = ChallengeUtility.createAnalysisItem1();
    BankStatementAnalysis bankStatementAnalysis = ChallengeUtility.createBankStatementAnalysis1(List.of(analysisItem, analysisItem, analysisItem, analysisItem, analysisItem));

    analysisItem.setBankStatementAnalysis(bankStatementAnalysis);
    bankStatement.setAnalysis(bankStatementAnalysis);
    user.setBankStatements(List.of(bankStatement, bankStatement, bankStatement));
    principal = () -> user.getEmail();
  }

  @Test
  void AutomaticChallenge_GetChallengeRecommendationsForUser() {
    // Arrange
    when(userRepository.findUserByEmailIgnoreCase(principal.getName())).thenReturn(java.util.Optional.of(user));

    // Act
    List<ChallengeRecommendationDto> listDto = automaticChallengeService.getChallengeRecommendationsForUser(principal);

    // Assert
    assertNotNull(listDto);
  }
}