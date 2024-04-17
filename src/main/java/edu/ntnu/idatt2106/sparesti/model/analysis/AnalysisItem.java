package edu.ntnu.idatt2106.sparesti.model.analysis;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * Represents an item in the analysis of a bank statement.
 *
 * @see BankStatementAnalysis
 */
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class AnalysisItem {
  @NonNull
  private SsbPurchaseCategory purchaseCategory;
  @NonNull
  private Double expectedValue;
  @NonNull
  private Double actualValue;

  @Override
  public String toString() {
    return "{category=" + this.getPurchaseCategory() + ", expected=" +
        this.getExpectedValue() + ", actual=" + this.getActualValue() + "}";
  }
}
