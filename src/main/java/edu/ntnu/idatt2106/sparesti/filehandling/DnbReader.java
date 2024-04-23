package edu.ntnu.idatt2106.sparesti.filehandling;

import edu.ntnu.idatt2106.sparesti.model.banking.BankStatement;
import java.io.File;
import lombok.extern.slf4j.Slf4j;

/**
 * Reader for HandelsBanken bank statements.
 */
@Slf4j
public class DnbReader extends BankStatementReader {
  @Override
  public BankStatement readStatement(File fileLocation) {
    throw new IllegalArgumentException("Method not implemented yet");
  }
}
