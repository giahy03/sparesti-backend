package edu.ntnu.idatt2106.sparesti.controller.analysis;

import edu.ntnu.idatt2106.sparesti.dto.analysis.TransactionDto;
import edu.ntnu.idatt2106.sparesti.service.analysis.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * Controller for managing transactions.
 * This controller is responsible for handling requests related to transactions.
 *
 * @version 1.0
 * @author Jeffrey Yaw Tabiri
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/transactions")
public class TransactionController {

  private final TransactionService transactionService;


  /**
   * Get a transaction by id.
   *
   * @param principal The principal of the user.
   * @param id The id of the transaction to get.
   * @return the transaction.
   */
  @GetMapping("/{id}")
  public ResponseEntity<TransactionDto> getTransaction(Principal principal, @RequestParam Long id) {
    log.info("Getting transaction with id: {} for user {}", id, principal.getName());
    TransactionDto transactionDto = transactionService.getTransaction(principal, id);
    log.info("Returning transaction with id: {} for user {}", id, principal.getName());
    return new ResponseEntity<>(transactionDto, HttpStatus.OK);
  }


  /**
   * Delete a transaction.
   *
   * @param principal The principal of the user.
   * @param id The id of the transaction to delete.
   * @return a response entity with status OK.
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteChallenge(Principal principal, @RequestParam Long id) {
    log.info("Deleting transaction with id: {} for user {}", id, principal.getName());
    transactionService.deleteTransaction(principal, id);
    log.info("Transaction with id: {} deleted for user {}", id, principal.getName());
    return new ResponseEntity<>(HttpStatus.OK);
  }


  /**
   * Update a transaction.
   *
   * @param principal The principal of the user.
   * @param id The id of the transaction to update.
   * @param transactionDto The transaction DTO.
   * @return a response entity with status OK.
   */
  @PutMapping("/{id}")
  public ResponseEntity<String> updateTransaction(Principal principal, @PathVariable Long id, @RequestBody TransactionDto transactionDto) {
    log.info("Updating transaction with id: {} for user {}", id, principal.getName());
    transactionService.updateTransaction(principal, id, transactionDto);
    log.info("Transaction with id: {} updated for user {}", id, principal.getName());
     return new ResponseEntity<>(HttpStatus.OK);
  }

}

