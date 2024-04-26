package edu.ntnu.idatt2106.sparesti.repository;

import edu.ntnu.idatt2106.sparesti.model.banking.Transaction;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


/**
 * Repository for the Transaction entity.
 *
 * @version 1.0
 * @see Transaction
 * @author Jeffrey Yaw Annor Tabiri
 */
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

  List<Transaction> findByBankStatement_AccountNumberAndBankStatement_User_Email(String accountNumber, String email, Pageable pageable);

}