package edu.ntnu.idatt2106.sparesti.controller.analysis;


import edu.ntnu.idatt2106.sparesti.dto.analysis.BankStatementAnalysisDto;
import edu.ntnu.idatt2106.sparesti.dto.analysis.BankStatementDto;
import edu.ntnu.idatt2106.sparesti.dto.analysis.TransactionDto;
import edu.ntnu.idatt2106.sparesti.exception.analysis.ExternalApiException;
import edu.ntnu.idatt2106.sparesti.mapper.AnalysisMapper;
import edu.ntnu.idatt2106.sparesti.mapper.BankStatementMapper;
import edu.ntnu.idatt2106.sparesti.model.analysis.AnalysisItem;
import edu.ntnu.idatt2106.sparesti.model.analysis.BankStatementAnalysis;
import edu.ntnu.idatt2106.sparesti.model.analysis.ssb.SsbPurchaseCategory;
import edu.ntnu.idatt2106.sparesti.model.banking.Bank;
import edu.ntnu.idatt2106.sparesti.model.banking.BankStatement;
import edu.ntnu.idatt2106.sparesti.model.user.User;
import edu.ntnu.idatt2106.sparesti.model.user.UserInfo;
import edu.ntnu.idatt2106.sparesti.service.analysis.BankStatementAnalysisService;
import edu.ntnu.idatt2106.sparesti.service.analysis.BankStatementService;
import edu.ntnu.idatt2106.sparesti.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.io.IOException;
import java.security.Principal;
import java.time.YearMonth;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * Controller for managing bank statements.
 * This controller is responsible for handling requests related to bank statements.
 *
 * @author Tobias Oftedal
 * @author Jeffrey Yaw Tabiri
 * @version 1.0
 * @since 1.0
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
  @Operation(summary = "Post a bank statement to the server")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "The bank statement was successfully "
          + "uploaded"),
      @ApiResponse(responseCode = "400", description = "Invalid parameters have been given, such "
          + "as an invalid file or an invalid bank name"),
      @ApiResponse(responseCode = "500", description = "The bank statement was not uploaded "
          + "because of an internal server error")})
  @PostMapping("/")
  public ResponseEntity<String> postBankStatement(MultipartFile file, Principal principal,
                                                  @RequestParam String bankName)
      throws IOException {

    if (file.getOriginalFilename() == null) {
      throw new IllegalArgumentException("The file has no name");
    }

    log.info("Received file: {} from user: {}", file.getOriginalFilename(), principal.getName());

    Bank bank;
    if (bankName == null || bankName.isEmpty()) {
      log.info("Bank name not found, defaulting to Handelsbanken");
      bank = Bank.HANDELSBANKEN;
    } else {
      try {
        bank = Bank.valueOf(bankName.toUpperCase());
      } catch (Exception e) {
        throw new IllegalArgumentException("The specified bank name is invalid");
      }
    }

    BankStatement savedBankStatement =
        bankStatementService.readAndSaveBankStatement(file, principal, bank);

    return ResponseEntity.ok(String.valueOf(savedBankStatement.getId()));
  }

  /**
   * Analyse a bank statement, if the bank statement has already been analysed, return the analysis.
   *
   * @param statementId the id of the bank statement to analyse
   * @param principal   the owner of the bank statement
   * @return the analysis of the bank statement
   */
  @Operation(summary = "Analyse a bank statement")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "The bank statement was successfully "
          + "analysed"),
      @ApiResponse(responseCode = "400", description = "Invalid parameters have been given, such "
          + "as an invalid file or an invalid bank name"),
      @ApiResponse(responseCode = "500", description = "The bank statement was not analysed "
          + "because of an internal server error")})
  @GetMapping("/{statementId}/analysis")
  public ResponseEntity<BankStatementAnalysisDto> analyseBankStatement(
      @PathVariable(name = "statementId") Long statementId, Principal principal,
      @RequestParam(defaultValue = "false") boolean forceNewAnalysis)
      throws ExternalApiException, NullPointerException {
    log.info("Analyzing bank statement with id: {} for user: {}", statementId, principal.getName());

    BankStatement statement = bankStatementService.getBankStatement(statementId, principal);

    if (statement.getAnalysis() != null && !forceNewAnalysis) {
      log.info("Bank statement has already been analyzed, returning the analysis");
      return ResponseEntity.ok(
          AnalysisMapper.INSTANCE.bankStatementAnalysisIntoBankStatementAnalysisDto(
              statement.getAnalysis()));
    }
    log.info("Bank statement has not been analyzed, analyzing now");

    User user = userService.findUser(principal.getName());
    UserInfo userInfo = user.getUserInfo();

    BankStatementAnalysis bankStatementAnalysis =
        bankStatementAnalysisService.analyze(statement, userInfo);
    bankStatementAnalysis.setBankStatement(statement);
    statement.setAnalysis(bankStatementAnalysis);
    BankStatement saved = bankStatementService.saveBankStatement(statement);

    BankStatementAnalysisDto bankStatementAnalysisDto =
        AnalysisMapper.INSTANCE.bankStatementAnalysisIntoBankStatementAnalysisDto(
            saved.getAnalysis());


    bankStatementAnalysisDto.getAnalysisItems()
        .forEach(analysisItemDto -> log.info(analysisItemDto.toString()));

    return ResponseEntity.ok(bankStatementAnalysisDto);
  }

  /**
   * Get all bank statements for a user.
   *
   * @param principal the user to get the bank statements for
   * @return a list of bank statements
   */
  @Operation(summary = "Get all bank statements for a user")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "The bank statements were successfully "
          + "retrieved"),
      @ApiResponse(responseCode = "500", description = "The bank statements were not retrieved "
          + "because of an internal server error")})
  @GetMapping("/")
  public ResponseEntity<List<BankStatementDto>> getAllStatementsForUser(Principal principal,
                                                                        @RequestParam(defaultValue = "0")
                                                                        Integer month,
                                                                        @RequestParam(defaultValue = "0")
                                                                        Integer year
  )
      throws NullPointerException {
    List<BankStatement> bankStatements = bankStatementService.getAllBankStatements(principal);

    if (month > 0 && year > 0) {
      return ResponseEntity.ok(bankStatements.stream()
          .filter(bankStatement -> bankStatement.getTimestamp().equals(YearMonth.of(year, month)))
          .map(BankStatementMapper.INSTANCE::bankStatementIntoBankStatementDto)
          .toList());
    }

    List<BankStatementDto> bankStatementDtoList =
        bankStatements.stream()
            .map(BankStatementMapper.INSTANCE::bankStatementIntoBankStatementDto)
            .toList();

    return ResponseEntity.ok(bankStatementDtoList);
  }


  /**
   * Get all account numbers for a user.
   *
   * @param principal the user to get the account numbers for
   * @return a set of account numbers
   */
  @Operation(summary = "Get all account numbers for a user")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "The account numbers were successfully "
          + "retrieved"),
      @ApiResponse(responseCode = "500", description = "The account numbers were not retrieved "
          + "because of an internal server error")})
  @GetMapping("/account-numbers")
  public ResponseEntity<Set<String>> getAllAccountNumbers(Principal principal) {
    log.info("Getting all account numbers for user: {}", principal.getName());
    return ResponseEntity.ok(bankStatementService.getAllAccountNumbers(principal));
  }

  /**
   * Endpoint for getting transactions.
   *
   * @param accountNumber is the account number we want to get the transactions from.
   * @param principal     is the user of the application.
   * @param page          is the page that you should extract.
   * @param pageSize      is the amount of transaction to view for each request.
   * @return a list of transactionDto.
   */
  @Operation(summary = "Get transactions for a user")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "The transactions were successfully "
          + "retrieved"),
      @ApiResponse(responseCode = "500", description = "The transactions were not retrieved "
          + "because of an internal server error")})
  @GetMapping("/transactions/{accountNumber}")
  public ResponseEntity<List<TransactionDto>> getTransactions(@PathVariable String accountNumber,
                                                              Principal principal,
                                                              @RequestParam int page,
                                                              @RequestParam int pageSize) {

    log.info("Getting transactions for account: {} for user: {}", accountNumber,
        principal.getName());
    List<TransactionDto> transactions =
        bankStatementService.getTransactions(accountNumber, principal, page, pageSize);
    log.info("Found {} transactions", transactions.size());
    return ResponseEntity.ok(transactions);
  }


  /**
   * Update the analysis of a bank statement.
   *
   * @param bankStatementAnalysisDto the analysis to update
   * @param principal                the user that owns the bank statement
   * @return the updated analysis
   */
  @Operation(summary = "Update the analysis of a bank statement")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "The analysis was successfully "
          + "updated"),
      @ApiResponse(responseCode = "400", description = "Invalid parameters have been given, such "
          + "as an invalid analysis"),
      @ApiResponse(responseCode = "500", description = "The analysis was not updated "
          + "because of an internal server error")})
  @PutMapping("/analyses")
  public ResponseEntity<BankStatementAnalysisDto> updateAnalysis(
      @RequestBody BankStatementAnalysisDto bankStatementAnalysisDto, Principal principal) {

    BankStatementAnalysis savedAnalysis =
        bankStatementService.getAllBankStatements(principal).stream()
            .filter(analysis -> analysis.getId().equals(bankStatementAnalysisDto.getId()))
            .map(BankStatement::getAnalysis).findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Analysis not found"));

    savedAnalysis.setAnalysisItems(
        bankStatementAnalysisDto.getAnalysisItems().stream().map(analysisItemDto -> {
          AnalysisItem item =
              new AnalysisItem(SsbPurchaseCategory.valueOf(analysisItemDto.getCategory()),
                  analysisItemDto.getExpectedValue(), analysisItemDto.getActualValue());
          item.setBankStatementAnalysis(savedAnalysis);
          return item;
        }).toList());
    bankStatementService.saveBankStatement(savedAnalysis.getBankStatement());
    return null;
  }

  /**
   * Delete a bank statement.
   *
   * @param statementId the id of the bank statement to delete
   * @param principal   the user that owns the bank statement
   * @return a response entity
   */
  @Operation(summary = "Delete a bank statement")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "The bank statement was successfully "
          + "deleted"),
      @ApiResponse(responseCode = "404", description = "Invalid parameters have been given, such "
          + "as an invalid bank statement id"),
      @ApiResponse(responseCode = "500", description = "The bank statement was not deleted "
          + "because of an internal server error")})
  @DeleteMapping("/{statementId}")
  public ResponseEntity<String> deleteBankStatement(
      @PathVariable(name = "statementId") Long statementId, Principal principal) throws
      NoSuchElementException {
    log.info("Deleting bank statement with id: {} for user: {}", statementId, principal.getName());
    bankStatementService.deleteBankStatement(statementId, principal);
    return ResponseEntity.ok("Bank statement deleted");
  }


}

