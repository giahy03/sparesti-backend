package edu.ntnu.idatt2106.sparesti.controller.analysis;


import edu.ntnu.idatt2106.sparesti.dto.analysis.BankStatementAnalysisDto;
import edu.ntnu.idatt2106.sparesti.dto.analysis.BankStatementDto;
import edu.ntnu.idatt2106.sparesti.exception.analysis.ExternalApiException;
import edu.ntnu.idatt2106.sparesti.dto.analysis.TransactionDto;
import edu.ntnu.idatt2106.sparesti.mapper.AnalysisMapper;
import edu.ntnu.idatt2106.sparesti.mapper.BankStatementMapper;
import edu.ntnu.idatt2106.sparesti.model.analysis.BankStatementAnalysis;
import edu.ntnu.idatt2106.sparesti.model.banking.BankStatement;
import edu.ntnu.idatt2106.sparesti.model.user.User;
import edu.ntnu.idatt2106.sparesti.model.user.UserInfo;
import edu.ntnu.idatt2106.sparesti.service.analysis.BankStatementAnalysisService;
import edu.ntnu.idatt2106.sparesti.service.analysis.BankStatementService;
import edu.ntnu.idatt2106.sparesti.service.user.UserService;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * Controller for managing bank statements.
 * This controller is responsible for handling requests related to bank statements.
 *
 * @version 1.0
 * @since 1.0
 * @author Tobias Oftedal
 * @author Jeffrey Yaw Tabiri
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/bank-statements")
public class BankStatementController {
  @NonNull
  private final BankStatementAnalysisService bankStatementAnalysisService;
  @NonNull
  private final BankStatementService bankStatementService;
  @NonNull
  private final UserService userService;

  /**
   * Post a bank statement to the server.
   *
   * @param file      the pdf bank statement to upload.
   * @param principal the user that uploaded the bank statement.
   * @return the id of the bank statement.
   * @throws IOException if there is an error reading the file.
   */
  @PostMapping("/")
  public ResponseEntity<String> postBankStatement(MultipartFile file,
                                                  Principal principal)
      throws IOException {

    if (file.getOriginalFilename() == null) {
      throw new IllegalArgumentException("The file has no name");
    }

    log.info("Received file: " + file.getOriginalFilename() + "from user: " + principal.getName());

    BankStatement savedBankStatement = bankStatementService.saveBankStatement(file, principal);

    return ResponseEntity.ok(String.valueOf(savedBankStatement.getId()));
  }

  /**
   * Analyse a bank statement, if the bank statement has already been analysed, return the analysis.
   *
   * @param statementId the id of the bank statement to analyse
   * @param principal   the owner of the bank statement
   * @return the analysis of the bank statement
   */
  @GetMapping("/{statementId}/analysis")
  public ResponseEntity<BankStatementAnalysisDto> analyseBankStatement(
      @PathVariable(name = "statementId") Long statementId,
      Principal principal
  ) throws ExternalApiException, NullPointerException {
    log.info(
        "Analyzing bank statement with id: " + statementId + " for user: " + principal.getName());

    BankStatement statement = bankStatementService.getBankStatement(statementId, principal);

    if (statement.getAnalysis() != null) {
      log.info("Bank statement has already been analyzed, returning the analysis");
      return ResponseEntity.ok(
          AnalysisMapper.INSTANCE.bankStatementAnalysisIntoBankStatementAnalysisDto(
              statement.getAnalysis()));
    }
    log.info("Bank statement has not been analyzed, analyzing now");

    User user = userService.findUser(principal.getName());
    UserInfo userInfo = user.getUserInfo();

    BankStatementAnalysis bankStatementAnalysis =
        bankStatementAnalysisService.analyze(
            statement,
            userInfo
        );
    bankStatementAnalysis.setBankStatement(statement);
    statement.setAnalysis(bankStatementAnalysis);
    BankStatement saved = bankStatementService.saveBankStatement(statement);

    BankStatementAnalysisDto bankStatementAnalysisDto =
        AnalysisMapper.INSTANCE.bankStatementAnalysisIntoBankStatementAnalysisDto(
            saved.getAnalysis());


    bankStatementAnalysisDto.getAnalysisItems().forEach(
        analysisItemDto -> log.info(analysisItemDto.toString())
    );

    return ResponseEntity.ok(
        bankStatementAnalysisDto
    );
  }

  /**
   * Get all bank statements for a user.
   *
   * @param principal the user to get the bank statements for
   * @return a list of bank statements
   */
  @GetMapping("/")
  public ResponseEntity<List<BankStatementDto>> getAllStatementsForUser(Principal principal)
      throws NullPointerException {
    List<BankStatement> bankStatements = bankStatementService.getAllBankStatements(principal);
    List<BankStatementDto> bankStatementDtoList =
        bankStatements
            .stream()
            .map(BankStatementMapper.INSTANCE::bankStatementIntoBankStatementDto)
            .toList();

    return ResponseEntity.ok(bankStatementDtoList);
  }

  @GetMapping("/statements")
  public ResponseEntity<Set<String>> getAllAccountNumbers(Principal principal) {
    log.info("Getting all account numbers for user: " + principal.getName());
    return ResponseEntity.ok(bankStatementService.getAllAccountNumbers(principal));
  }

  /**
   * Endpoint for getting transactions.
   *
   * @param accountNumber is the account number we want to get the transactions from.
   * @param principal is the user of the application.
   * @param page is the page that you should extract.
   * @param pageSize is the amount of transaction to view for each request.
   * @return a list of transactionDto.
   */
  @GetMapping("/transactions/{accountNumber}")
  public ResponseEntity<List<TransactionDto>> getTransactions(@PathVariable String accountNumber,
                                                              Principal principal, @RequestParam int page,
                                                              @RequestParam int pageSize) {

    log.info("Getting transactions for account: " + accountNumber + " for user: " + principal.getName());
    List<TransactionDto> transactions = bankStatementService.getTransactions(accountNumber, principal, page, pageSize);
    log.info("Found " + transactions.size() + " transactions");
    return ResponseEntity.ok(transactions);
  }

}
