package filehandling;

import edu.ntnu.idatt2106.sparesti.model.banking.BankStatement;
import java.nio.file.Path;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HandelsBankenReader extends BankStatementReader {
  @Override
  public BankStatement readStatement(Path fileLocation) {
    throw new IllegalArgumentException("Method not implemented yet");
  }
}
