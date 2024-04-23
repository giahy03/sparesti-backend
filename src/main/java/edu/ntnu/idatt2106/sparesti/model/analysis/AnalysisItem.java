package edu.ntnu.idatt2106.sparesti.model.analysis;

import edu.ntnu.idatt2106.sparesti.model.analysis.ssb.SsbPurchaseCategory;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.Objects;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Represents an item in the analysis of a bank statement.
 *
 * @see BankStatementAnalysis
 */
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name = "analysis_items")
@Entity
@Getter
@Setter
@ToString
public class AnalysisItem {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @NonNull
  @Enumerated(EnumType.STRING)
  @Column(name = "purchase_category")
  @Schema(description = "The purchase category of the item.")
  private SsbPurchaseCategory purchaseCategory;

  @NonNull
  @Column(name = "expected_value")
  @Schema(description = "The expected value of the item.")
  private Double expectedValue;

  @NonNull
  @Column(name = "actual_value")
  @Schema(description = "The actual value of the item.")
  private Double actualValue;

  @ManyToOne
  @ToString.Exclude
  private BankStatementAnalysis bankStatementAnalysis;

  @Override
  public final boolean equals(Object object) {
    if (this == object) {
      return true;
    }
    if (object == null) {
      return false;
    }
    AnalysisItem that = (AnalysisItem) object;
    return id != null && Objects.equals(id, that.id);
  }
}


