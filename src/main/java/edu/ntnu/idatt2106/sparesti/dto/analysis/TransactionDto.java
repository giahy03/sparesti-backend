package edu.ntnu.idatt2106.sparesti.dto.analysis;

import edu.ntnu.idatt2106.sparesti.model.analysis.ssb.SsbPurchaseCategory;
import java.time.MonthDay;
import lombok.Builder;
import lombok.Data;

/**
 * Data transfer object for Transaction.
 * The class is used to transfer data between the frontend and the backend.
 *
 * @author Tobias Oftedal
 * @author Jeffrey Yaw Annor Tabiri
 * @version 1.0
 */
@Data
@Builder
public class TransactionDto {
  private Long id;
  private MonthDay date;
  private String description;
  private Double amount;
  private Boolean isIncoming;
  private SsbPurchaseCategory category;
}
