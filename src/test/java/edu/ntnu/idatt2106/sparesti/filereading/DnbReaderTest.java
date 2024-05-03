package edu.ntnu.idatt2106.sparesti.filereading;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import edu.ntnu.idatt2106.sparesti.filehandling.DnbReader;
import edu.ntnu.idatt2106.sparesti.model.banking.BankStatement;
import java.io.File;
import org.junit.jupiter.api.Test;

/**
 * Test class for the DnbReader class.
 *
 * @version 1.0
 * @author Jeffrey Yaw Annor Tabiri
 */
class DnbReaderTest {

  @Test
  void givenInvalidDnbPdf_whenReadFile_throwsException() {
    //arrange
    DnbReader dnbReader = new DnbReader();
    String path = "src/test/resources/bankstatements/invalid_pdf.pdf";
    File invalidFile = new File(path);
    //act
    assertThrows(IllegalArgumentException.class, () -> dnbReader.readStatement(invalidFile));
  }


  @Test
  void givenDnbPdf_whenReadFile_bankStatementIsCorrect() {
    //arrange
    DnbReader dnbReader = new DnbReader();
    String path = "src/main/resources/bankstatements/dnb/dnbExample.pdf";
    //act
    BankStatement bankStatement = dnbReader.readStatement(
            new File(path));
    //assert
    assertEquals("1207.84.17631", bankStatement.getAccountNumber());
    //TODO, check that this number should actually be 192
    assertEquals(192, bankStatement.getTransactions().size());
  }
}
