package edu.ntnu.idatt2106.sparesti.filereading;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import edu.ntnu.idatt2106.sparesti.filehandling.HandelsBankenReader;
import edu.ntnu.idatt2106.sparesti.model.banking.BankStatement;
import java.io.File;
import org.junit.jupiter.api.Test;

class HandelsbankenReaderTest {

  @Test
  void givenSparebank1Pdf_whenReadFile_bankStatementIsCorrect() {
    //arrange
    HandelsBankenReader handelsbankenReader = new HandelsBankenReader();
    String path = "src/main/resources/bankstatements/sparebank1/sparebank1example.pdf";
    //act
    BankStatement bankStatement = handelsbankenReader.readStatement(new File(path));

    //assert
    assertEquals("3335.31.00535", bankStatement.getAccountNumber());
    assertEquals(32, bankStatement.getTransactions().size());

  }


  @Test
  void givenHandelsbankenPdf_whenReadFile_bankStatementIsCorrect() {
    //arrange
    HandelsBankenReader handelsbankenReader = new HandelsBankenReader();
    String path = "src/main/resources/bankstatements/handelsbanken/handelsbankenExample.pdf";
    //act
    BankStatement bankStatement = handelsbankenReader.readStatement(new File(path));
    //assert
    assertEquals("9051.12.20288", bankStatement.getAccountNumber());
    assertEquals(41, bankStatement.getTransactions().size());
  }

  @Test
  void givenInvalidHandelsBankenPdf_whenReadFile_throwsException() {
    //arrange
    HandelsBankenReader handelsbankenReader = new HandelsBankenReader();
    String path = "src/test/resources/bankstatements/invalid_pdf.pdf";
    File invalidFile = new File(path);
    //act
    assertThrows(IllegalArgumentException.class, () -> handelsbankenReader.readStatement(invalidFile));
  }


}
