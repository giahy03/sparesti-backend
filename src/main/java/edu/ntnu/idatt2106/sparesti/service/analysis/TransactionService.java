package edu.ntnu.idatt2106.sparesti.service.analysis;

import edu.ntnu.idatt2106.sparesti.dto.analysis.TransactionDto;
import edu.ntnu.idatt2106.sparesti.mapper.TransactionMapper;
import edu.ntnu.idatt2106.sparesti.model.analysis.ssb.SsbPurchaseCategory;
import edu.ntnu.idatt2106.sparesti.model.banking.Transaction;
import edu.ntnu.idatt2106.sparesti.repository.TransactionRepository;
import edu.ntnu.idatt2106.sparesti.repository.user.UserRepository;
import edu.ntnu.idatt2106.sparesti.service.analysis.openai.OpenAiService;
import jakarta.validation.constraints.NotBlank;
import java.net.SocketTimeoutException;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Classifies a transaction into a category.
 */
@Slf4j
@Service
@AllArgsConstructor
public class TransactionService {
  OpenAiService openAiService;

  TransactionRepository transactionRepository;

  UserRepository userRepository;

  private static final String ASSISTANT_ID = "asst_NgkqP4xlGYgjOsfZNgrLQs4Z";


  /**
   * Categorizes a list of transactions.
   *
   * @param transactions The transactions to categorize.
   */
  public void categorizeTransactions(@NonNull List<Transaction> transactions)
      throws NullPointerException,
      SocketTimeoutException, IndexOutOfBoundsException {


    String transactionsString =
        transactions.stream().map(Transaction::getDescription)
            .collect(Collectors.joining("|"));

    try {
      String response = openAiService.sendMessage(transactionsString, ASSISTANT_ID);
      log.info("Received response: {}", response);


      setCategories(transactions,
          response
      );

    } catch (Exception e) {
      log.error("Could not categorize transactions", e);
      throw e;
    }
  }

  /**
   * Parses the response from the OpenAI API.
   *
   * @param response The response to parse.
   * @return A map of the index of the transaction and the category it was classified as.
   */
  private HashMap<Integer, Optional<SsbPurchaseCategory>> parseResponse(
      @NonNull @NotBlank String response)
      throws IndexOutOfBoundsException, NullPointerException {

    log.info("Parsing open ai response");

    HashMap<Integer, Optional<SsbPurchaseCategory>> categoryConnections = new HashMap<>();
    String[] pairs = response
        .substring(1, response.length() - 1)
        .trim()
        .split("\\|");
    log.info("Amount of pairs received: " + pairs.length);
    for (String pair : pairs) {
      try {
        String[] parts = pair.split("§");
        Integer index = Integer.parseInt(parts[0]);
        SsbPurchaseCategory category = SsbPurchaseCategory.valueOf(parts[1]);
        categoryConnections.put(index, Optional.of(category));
      } catch (Exception e) {
        log.error("Could not parse pair: " + pair + " because: " + e.getMessage());
      }
    }

    return categoryConnections;
  }

  /**
   * Sets the categories of the transactions.
   *
   * @param transactions The transactions to set the categories of.
   * @param response     The response from the OpenAI API.
   */
  private void setCategories(List<Transaction> transactions, String response) {
    HashMap<Integer, Optional<SsbPurchaseCategory>> categoryConnections = parseResponse(response);
    for (int i = 0; i < transactions.size(); i++) {
      Transaction transaction = transactions.get(i);
      Optional<SsbPurchaseCategory> category = Optional.empty();
      if (categoryConnections.containsKey(i)) {
        category = categoryConnections.get(i);
      }
      if (category.isPresent()) {
        transaction.setCategory(category.get());
        log.info(
            transaction.getDescription() + " was categorized as: " + transaction.getCategory());
      } else {
        transaction.setCategory(SsbPurchaseCategory.OTHER);
        log.error("There was no category to set for: " + transaction.getDescription());
      }
    }
  }



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
