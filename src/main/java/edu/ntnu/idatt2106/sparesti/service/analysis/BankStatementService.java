package edu.ntnu.idatt2106.sparesti.service.analysis;

import edu.ntnu.idatt2106.sparesti.dto.analysis.TransactionDto;
import edu.ntnu.idatt2106.sparesti.exception.auth.UnauthorizedOperationException;
import edu.ntnu.idatt2106.sparesti.filehandling.HandelsBankenReader;
import edu.ntnu.idatt2106.sparesti.mapper.TransactionMapper;
import edu.ntnu.idatt2106.sparesti.model.banking.BankStatement;
import edu.ntnu.idatt2106.sparesti.model.banking.Transaction;
import edu.ntnu.idatt2106.sparesti.model.user.User;
import edu.ntnu.idatt2106.sparesti.repository.BankStatementRepository;
import edu.ntnu.idatt2106.sparesti.repository.TransactionRepository;
import edu.ntnu.idatt2106.sparesti.repository.user.UserRepository;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.Principal;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * Service class for the BankStatement entity.
 *
 * @version 1.0
 * @author Jeffrey Yaw Annor Tabiri
 * @author Tobias Oftedal
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class BankStatementService {
  @NonNull
  private final UserRepository userRepository;
  @NonNull
  private final BankStatementRepository bankStatementRepository;
  @NonNull
  private final TransactionRepository transactionRepository;

  /**
   * Saves a bank statement.
   *
   * @param bankStatement The bank statement to save.
   * @return The saved bank statement.
   */
  public BankStatement saveBankStatement(BankStatement bankStatement) {
    return bankStatementRepository.save(bankStatement);
  }

  /**
   * Saves a bank statement from a file.
   *
   * @param file      The file to read the bank statement from.
   * @param principal The principal of the user.
   * @return The saved bank statement.
   * @throws NoSuchElementException If the user is not found.
   * @throws IOException            If the file cannot be read properly.
   */
  public BankStatement saveBankStatement(MultipartFile file, Principal principal)
      throws NoSuchElementException, IOException, NullPointerException {

    User user = userRepository.findUserByEmailIgnoreCase(principal.getName())
        .orElseThrow(() -> new UsernameNotFoundException("User not found"));

    HandelsBankenReader spareBank1Reader = new HandelsBankenReader();
    File tempFile = File.createTempFile("bank-statement-", "-" + file.getOriginalFilename());
    file.transferTo(tempFile);

    BankStatement bankStatement = spareBank1Reader.readStatement(tempFile);


    if (file.getOriginalFilename() == null){
      bankStatement.setFileName("unnamed-bank-statement" + bankStatement.getTimestamp());
    } else {
      bankStatement.setFileName(file.getOriginalFilename());
    }

    Files.delete(tempFile.toPath());

    bankStatement.setUser(user);
    bankStatement.getTransactions()
        .forEach(transaction -> transaction.setBankStatement(bankStatement));

    return bankStatementRepository.save(bankStatement);
  }

  /**
   * Gets a bank statement by id. If the user is not authorized to access the bank statement, an
   * UnauthorizedOperationException is thrown.
   *
   * @param statementId The id of the bank statement.
   * @param principal   The principal of the user.
   * @return The bank statement.
   * @throws NoSuchElementException         If the user or bank statement is not found.
   * @throws UnauthorizedOperationException If the user is not authorized to access the bank
   */
  public BankStatement getBankStatement(Long statementId, Principal principal)
      throws NoSuchElementException, UnauthorizedOperationException {
    User user = userRepository.findUserByEmailIgnoreCase(principal.getName())
        .orElseThrow(() -> new NoSuchElementException("User not found"));
    BankStatement bankStatement = bankStatementRepository.findByIdAndUser(statementId, user)
        .orElseThrow(() -> new NoSuchElementException("Bank statement not found"));

    if (!bankStatement.getUser().equals(user)) {
      throw new UnauthorizedOperationException(
          "User is not authorized to access this bank " + "statement");
    }
    return bankStatement;
  }

  /**
   * Gets all bank statements for a user.
   *
   * @param principal The principal of the user.
   * @return A list of bank statements for the user.
   */
  public List<BankStatement> getAllBankStatements(Principal principal) {
    User user = userRepository.findUserByEmailIgnoreCase(principal.getName())
        .orElseThrow(() -> new NoSuchElementException("User not found"));
    return bankStatementRepository.findAllByUser(user);
  }

  public Set<String> getAllAccountNumbers(Principal principal) {
    User user = userRepository.findUserByEmailIgnoreCase(principal.getName())
        .orElseThrow(() -> new UsernameNotFoundException("User not found"));

    List<BankStatement> bankStatements = bankStatementRepository.findAllByUser(user);

    Set<String> accountNumbers = new  HashSet<>();

    for (BankStatement bankStatement : bankStatements) {
      accountNumbers.add(bankStatement.getAccountNumber());
    }

    return accountNumbers;
  }

  public List<TransactionDto> getTransactions(String accountNumber, Principal principal, int pageNumber, int pageSize) {

    PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
    List<Transaction> bankStatement = transactionRepository
            .findByBankStatement_AccountNumberAndBankStatement_User_Email
                    (accountNumber, principal.getName(), pageRequest);

    List<TransactionDto> transactionDtoList = new ArrayList<>();

    for (Transaction transaction : bankStatement) {
      Month month = transaction.getDate().getMonth(); // Assuming getMonth() returns the month (0-indexed)
      int day = transaction.getDate().getDayOfMonth(); // Assuming getDay() returns the day of the month
      int year = transaction.getBankStatement().getTimestamp().getYear();

      TransactionDto transactionDto = TransactionMapper.INSTANCE.transactionToTransactionDto(transaction);
      transactionDto.setFullDate(LocalDate.of(year, month, day));
      transactionDtoList.add(transactionDto);
    }
    return transactionDtoList;
  }

}
