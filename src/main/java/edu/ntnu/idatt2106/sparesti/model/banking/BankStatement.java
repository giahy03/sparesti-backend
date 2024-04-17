package edu.ntnu.idatt2106.sparesti.model.banking;

import edu.ntnu.idatt2106.sparesti.model.user.User;
import java.time.YearMonth;
import java.util.List;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * Class representing a bank statement for a given month.
 */
@Data
@ToString
@RequiredArgsConstructor
public class BankStatement {
  @NonNull
  private User user;
  @NonNull
  private String accountNumber;
  @NonNull
  private List<Transaction> transactions;
  @NonNull
  private YearMonth yearMonth;

}
