package edu.ntnu.idatt2106.sparesti.service;

import edu.ntnu.idatt2106.sparesti.dto.analysis.TransactionDto;
import edu.ntnu.idatt2106.sparesti.mapper.TransactionMapper;
import edu.ntnu.idatt2106.sparesti.model.banking.Transaction;
import edu.ntnu.idatt2106.sparesti.repository.TransactionRepository;
import edu.ntnu.idatt2106.sparesti.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.security.Principal;
import java.util.NoSuchElementException;

/**
 * Service class for the Transaction entity.
 *
 * @version 1.0
 * @author Jeffrey Yaw Annor Tabiri
 */
@Service
@RequiredArgsConstructor
public class TransactionService {
  TransactionRepository transactionRepository;
  UserRepository userRepository;

  private void checkIfUserIsAuthorized(Principal principal, Transaction transaction) {
    if (!transaction.getBankStatement().getUser().getEmail().equals(principal.getName())) {
      throw new IllegalArgumentException("User is not authorized to delete this transaction");
    }
  }

  private void checkIfUserExists(Principal principal) {
    if (userRepository.findUserByEmailIgnoreCase(principal.getName()).isEmpty()) {
      throw new UsernameNotFoundException("User not found");
    }
  }


  /**
   * Deletes a transaction.
   * @param principal The principal of the user.
   * @param id The id of the transaction to delete.
   */
  public void deleteTransaction(Principal principal, Long id) {

    Transaction transaction = transactionRepository.findById(id).orElseThrow(
            () -> new NoSuchElementException("Transaction not found."));
    checkIfUserExists(principal);
    checkIfUserIsAuthorized(principal, transaction);
    transactionRepository.deleteById(id);
  }

  /**
   * Updates a transaction.
   *
   * @param principal The principal of the user.
   * @param id The id of the transaction to update.
   * @param transactionDto The transaction DTO.
   */
  public void updateTransaction(Principal principal, Long id, TransactionDto transactionDto) {
    Transaction transaction = transactionRepository.findById(id).orElseThrow(
            () -> new NoSuchElementException("Transaction not found."));
    checkIfUserExists(principal);
    checkIfUserIsAuthorized(principal, transaction);

    // Update transaction
    transaction.setCategory(transactionDto.getCategory());
    transaction.setAmount(transactionDto.getAmount());
    transaction.setDate(transactionDto.getDate());
    transaction.setIsIncoming(transactionDto.getIsIncoming());
    transaction.setDescription(transactionDto.getDescription());

    transactionRepository.save(transaction);
  }


  /**
   * Retrieves a transaction.
   *
   * @param principal The principal of the user.
   * @param id The id of the transaction to retrieve.
   * @return The transaction DTO.
   */
  public TransactionDto getTransaction(Principal principal, Long id) {
    Transaction transaction = transactionRepository.findById(id).orElseThrow(
            () -> new NoSuchElementException("Transaction not found."));
    checkIfUserExists(principal);
    checkIfUserIsAuthorized(principal, transaction);

    return TransactionMapper.INSTANCE.transactionToTransactionDto(transaction);
  }

}
