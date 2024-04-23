package edu.ntnu.idatt2106.sparesti.dto.analysis;

import edu.ntnu.idatt2106.sparesti.model.analysis.ssb.SsbPurchaseCategory;
import java.time.MonthDay;
import lombok.Data;

/**
 * Data transfer object for Transaction.
 */
@Data
public class TransactionDto {
  private Long id;
  private MonthDay date;
  private String description;
  private Double amount;
  private Boolean isIncoming;
  private SsbPurchaseCategory category;
}
