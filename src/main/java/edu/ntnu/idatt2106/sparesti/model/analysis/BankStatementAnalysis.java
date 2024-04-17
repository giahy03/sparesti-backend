package edu.ntnu.idatt2106.sparesti.model.analysis;

import java.util.List;
import lombok.Data;
import lombok.NonNull;

/**
 * Represents the analysis of a bank statement.
 */
@Data
public class BankStatementAnalysis {
  @NonNull
  List<AnalysisItem> analysisItems;

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("BankStatementAnalysis");
    analysisItems.forEach(item -> sb.append(item).append("\n"));

    return sb.toString();
  }
}
