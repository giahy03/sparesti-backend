package edu.ntnu.idatt2106.sparesti.dto.analysis;

import java.time.YearMonth;
import java.util.List;
import lombok.Data;

/**
 * Data transfer object for BankStatement.
 */
@Data
public class BankStatementDto {
  private Long id;
  private List<TransactionDto> transactions;
  private String accountNumber;
  private YearMonth timestamp;
  private boolean analysisIsPresent;
}
