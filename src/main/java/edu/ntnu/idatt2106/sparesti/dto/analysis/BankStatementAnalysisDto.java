package edu.ntnu.idatt2106.sparesti.dto.analysis;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Data transfer object for BankStatementAnalysis.
 */
@Data
public class BankStatementAnalysisDto {
  private Long id;
  private List<AnalysisItemDto> analysisItems;
}
