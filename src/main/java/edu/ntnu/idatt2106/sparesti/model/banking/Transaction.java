package edu.ntnu.idatt2106.sparesti.model.banking;

import java.time.MonthDay;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * Class representing a transaction in a bank statement.
 */
@Data
@RequiredArgsConstructor
public class Transaction {
  @NonNull
  private MonthDay date;
  @NonNull
  private String description;
  @NonNull
  private Double amount;
  @NonNull
  private Boolean isIncoming;
}

