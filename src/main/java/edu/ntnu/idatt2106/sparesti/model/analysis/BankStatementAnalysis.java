package edu.ntnu.idatt2106.sparesti.model.analysis;

import edu.ntnu.idatt2106.sparesti.model.banking.BankStatement;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.List;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * Represents the analysis of a bank statement.
 */
@Getter
@Setter
@Table(name = "bank_statement_analyses")
@Entity
@NoArgsConstructor
@RequiredArgsConstructor
public class BankStatementAnalysis {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @NonNull
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "bankStatementAnalysis")
  private List<AnalysisItem> analysisItems;

  @OneToOne(cascade = CascadeType.ALL, mappedBy = "analysis")
  private BankStatement bankStatement;

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("BankStatementAnalysis");
    analysisItems.forEach(item -> sb.append(item).append("\n"));

    return sb.toString();
  }
}
