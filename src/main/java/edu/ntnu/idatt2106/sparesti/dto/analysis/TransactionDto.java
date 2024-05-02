package edu.ntnu.idatt2106.sparesti.dto.analysis;

import edu.ntnu.idatt2106.sparesti.model.analysis.ssb.SsbPurchaseCategory;

import java.time.LocalDate;
import java.time.MonthDay;

import lombok.Builder;
import lombok.Data;
import org.springframework.cglib.core.Local;

/**
 * Data transfer object for Transaction.
 * The class is used to transfer data between the frontend and the backend.
 *
 * @version 1.0
 * @author Tobias Oftedal
 * @author Jeffrey Yaw Annor Tabiri
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
