package edu.ntnu.idatt2106.sparesti.service.analysis;

import edu.ntnu.idatt2106.sparesti.exception.auth.UnauthorizedOperationException;
import edu.ntnu.idatt2106.sparesti.filehandling.HandelsBankenReader;
import edu.ntnu.idatt2106.sparesti.model.banking.BankStatement;
import edu.ntnu.idatt2106.sparesti.model.user.User;
import edu.ntnu.idatt2106.sparesti.repository.BankStatementRepository;
import edu.ntnu.idatt2106.sparesti.repository.user.UserRepository;
import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.NoSuchElementException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * Service class for the BankStatement entity.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class BankStatementService {
  @NonNull
  private final UserRepository userRepository;
  @NonNull
  private final BankStatementRepository bankStatementRepository;

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
      throws NoSuchElementException, IOException {


    HandelsBankenReader spareBank1Reader = new HandelsBankenReader();
    File tempFile = File.createTempFile("bank-statement-", "-" + file.getOriginalFilename());
    file.transferTo(tempFile);

    BankStatement bankStatement = spareBank1Reader.readStatement(tempFile);

    tempFile.delete();

    User user = userRepository.findUserByEmailIgnoreCase(principal.getName())
        .orElseThrow(() -> new NoSuchElementException("User not found"));
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
}
