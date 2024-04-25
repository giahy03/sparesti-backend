package edu.ntnu.idatt2106.sparesti.filehandling;

import edu.ntnu.idatt2106.sparesti.model.banking.BankStatement;
import edu.ntnu.idatt2106.sparesti.model.banking.Transaction;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.MonthDay;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.util.Pair;

/**
 * Reader for HandelsBanken bank statements.
 */
@Slf4j
public class DnbReader extends BankStatementReader {
  BankStatement readStatementFromPath(Path fileLocation) {
    log.info("Reading SpareBank1 statement from file:" + fileLocation.toString());

    return readStatement(fileLocation.toFile());
  }

  @Override
  public void readFirstPage(String pageText, BankStatement bankStatement) {

    String[] splitText = pageText.split("\n");

    int lineIndex = 0;
    Pair<Optional<String>, Integer> accountLine =
        skipUntilFind("kontoutskrift", splitText, lineIndex);

    if (accountLine.getFirst().isEmpty()) {
      throw new IllegalArgumentException("Could not find account number in DNB statement");
    }
    bankStatement.setAccountNumber(accountLine.getFirst().get().split("\\s+")[2]);
    lineIndex = accountLine.getSecond();


    skipUntilFind("bruk", splitText, lineIndex);


    String currentString = "";
    for (; lineIndex < splitText.length; lineIndex++) {
      String line = splitText[lineIndex];
      currentString = currentString + " " +line;

      try {
        parseTransaction(currentString, false);
        System.out.println(currentString + " was a valid transaction");
        currentString = "";
      } catch (Exception e) {
        log.info("could not parse: " + e.getMessage());
      }

    }

  }

  @Override
  public void readStandardPage(String pageText, BankStatement bankStatement) {
//    if (0 == 0){
//      return;
//    }
//    String[] splitText = pageText.split("\n");
//
//    for (String s : splitText) {
//
//      String[] splitLine = s.split(" ");
//
//      try {
//        Integer.parseInt(splitLine[splitLine.length - 1]);
//        Transaction transaction;
//        if (s.toLowerCase().contains("fra")) {
//          transaction = parseTransaction(s, true);
//        } else {
//          transaction = parseTransaction(s, false);
//        }
//        bankStatement.getTransactions().add(transaction);
//      } catch (Exception e) {
//        log.info("Error while reading standard page: " + e.getMessage());
//      }
//    }
  }


  /**
   * Parses a transaction from a line in the SpareBank1 pdf.
   *
   * @param line     The line to parse.
   * @param incoming Whether the transaction is incoming or outgoing.
   * @return The parsed transaction.
   */
  private Transaction parseTransaction(String line, boolean incoming) {
    log.info("Parsing transaction: " + line);
    String[] splitLine = line.split(" ");


    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy");

    String purchaseDateString = splitLine[0];
    String bookKeepingDateString = splitLine[1];

    LocalDate purchaseDate = LocalDate.parse(purchaseDateString, formatter);
    LocalDate bookKeepingDate = LocalDate.parse(bookKeepingDateString, formatter);


    String dateLine = splitLine[1];
    String transactionSum = splitLine[splitLine.length - 3];
    Integer archiveReference = Integer.parseInt(splitLine[splitLine.length - 1]);


    return null;
  }
}
