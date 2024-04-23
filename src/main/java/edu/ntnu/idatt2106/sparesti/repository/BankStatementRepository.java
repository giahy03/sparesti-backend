package edu.ntnu.idatt2106.sparesti.repository;

import edu.ntnu.idatt2106.sparesti.model.banking.BankStatement;
import edu.ntnu.idatt2106.sparesti.model.user.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for the BankStatement entity.
 */
public interface BankStatementRepository extends JpaRepository<BankStatement, Long> {
  Optional<BankStatement> findByIdAndUser(Long id, User user);

  List<BankStatement> findAllByUser(User user);
}
