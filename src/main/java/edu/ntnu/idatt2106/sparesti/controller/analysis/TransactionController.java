package edu.ntnu.idatt2106.sparesti.controller.analysis;

import edu.ntnu.idatt2106.sparesti.dto.analysis.TransactionDto;
import edu.ntnu.idatt2106.sparesti.service.analysis.TransactionService;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for managing transactions.
 * This controller is responsible for handling requests related to transactions.
 *
 * @author Jeffrey Yaw Tabiri
 * @version 1.0
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
   * @param id        The id of the transaction to get.
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
   * @return a response entity with status OK.
   */
  @DeleteMapping("/")
  public ResponseEntity<String> deleteTransaction(Principal principal,
                                                  @RequestBody TransactionDto transactionDto) {
    log.info("Deleting transaction with id: {} for user {}", transactionDto.getId(),
        principal.getName());
    transactionService.deleteTransaction(principal, transactionDto.getId());
    log.info("Transaction with id: {} deleted for user {}", transactionDto.getId(),
        principal.getName());
    return new ResponseEntity<>(HttpStatus.OK);
  }


  /**
   * Update a transaction.
   *
   * @param principal      The principal of the user.
   * @param transactionDto The transaction DTO.
   * @return a response entity with status OK.
   */
  @PutMapping("/")
  public ResponseEntity<String> updateTransaction(Principal principal,
                                                  @RequestBody TransactionDto transactionDto) {
    log.info("Updating transaction with id: {} for user {}", transactionDto.getId(),
        principal.getName());
    transactionService.updateTransaction(principal, transactionDto);
    log.info("Transaction with id: {} updated for user {}", transactionDto.getId(),
        principal.getName());
    return new ResponseEntity<>(HttpStatus.OK);
  }

}

