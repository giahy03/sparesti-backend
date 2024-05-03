package edu.ntnu.idatt2106.sparesti.controller.analysis;

import edu.ntnu.idatt2106.sparesti.dto.analysis.TransactionDto;
import edu.ntnu.idatt2106.sparesti.service.analysis.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
   * Delete a transaction.
   *
   * @param principal The principal of the user.
   * @return a response entity with status OK.
   */
  @Operation(summary = "Delete a transaction")
  @ApiResponse(responseCode = "200", description = "Transaction deleted")
  @ApiResponse(responseCode = "400", description = "The transaction could not be deleted because "
      + "of a bad request, such as the user not owning the transaction")
  @ApiResponse(responseCode = "404", description = "The transaction could not be found")
  @ApiResponse(responseCode = "500", description = "The transaction could not be deleted because "
      + "of an internal server error")
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
  @Operation(summary = "Update a transaction")
  @ApiResponse(responseCode = "200", description = "Transaction updated")
  @ApiResponse(responseCode = "400", description = "The transaction could not be updated because "
      + "of a bad request, such as the user not owning the transaction")
  @ApiResponse(responseCode = "404", description = "The transaction could not be found")
  @ApiResponse(responseCode = "500", description = "The transaction could not be updated because "
      + "of an internal server error")
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

