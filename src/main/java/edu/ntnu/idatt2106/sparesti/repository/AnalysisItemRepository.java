package edu.ntnu.idatt2106.sparesti.repository;

import edu.ntnu.idatt2106.sparesti.model.analysis.AnalysisItem;
import edu.ntnu.idatt2106.sparesti.model.analysis.BankStatementAnalysis;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for the {@link AnalysisItem} object.
 * The interface extends JpaRepository and allows
 * basic CRUD (Create, Read, Update, Delete) operations.
 *
 * @author Tobias Oftedal
 * @version 1.0
 */
@Repository
public interface AnalysisItemRepository extends JpaRepository<AnalysisItem, Long> {
  void deleteAnalysisItemByBankStatementAnalysis(BankStatementAnalysis analysis);

  List<AnalysisItem> findAllByBankStatementAnalysis(BankStatementAnalysis analysis);
}

