package edu.ntnu.idatt2106.sparesti.filehandling;

import edu.ntnu.idatt2106.sparesti.model.banking.BankStatement;
import edu.ntnu.idatt2106.sparesti.model.banking.Transaction;
import java.time.MonthDay;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.util.Pair;

/**
 * Reader for HandelsBanken bank statements.
 *
 * @author Tobias Offtedal
 */
@Slf4j
public class DnbReader extends BankStatementReader {
  private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy");

  /**
   * Reads the first page of a DNB bank statement.
   *
   * @param pageText      The text of the page.
   * @param bankStatement The bank statement to read into.
   */
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

    readTransactions(bankStatement, splitText, lineIndex);
  }

  /**
   * Reads a standard page of a DNB bank statement.
   *
   * @param pageText      The text of the page.
   * @param bankStatement The bank statement to read into.
   */
  @Override
  public void readStandardPage(String pageText, BankStatement bankStatement) {
    log.debug("Reading new page");

    String[] splitText = pageText.split("\n");
    int lineIndex = 0;
    readTransactions(bankStatement, splitText, lineIndex);
  }

  /**
   * Reads transactions from a DNB bank statement.
   *
   * @param bankStatement The bank statement to read into.
   * @param splitText     The text of the page split into lines.
   * @param lineIndex     The index of the line to start reading from.
   */
  private void readTransactions(BankStatement bankStatement, String[] splitText, int lineIndex) {
    Pair<Optional<String>, Integer> lastMetaLine =
        skipUntilFind("bruk bokføring", splitText, lineIndex);
    lineIndex = lastMetaLine.getSecond();

    if (lastMetaLine.getFirst().isEmpty()) {
      throw new IllegalArgumentException("Could not find last line of metadata in the DNB "
          + "statement");
    }

    String currentString = "";
    for (; lineIndex < splitText.length; lineIndex++) {

      String line = splitText[lineIndex];

      currentString = currentString + " " + line;

      if (currentString.isBlank()) {
        continue;
      }
      try {
        Transaction parsedTransaction = parseTransaction(currentString, true);

        bankStatement.getTransactions().add(parsedTransaction);

        log.debug(currentString + " was a valid transaction");
        currentString = "";
      } catch (Exception e) {
        log.debug("could not parse: " + e);
      }

    }
  }

  /**
   * Parses a transaction from a line in the SpareBank1 pdf.
   *
   * @param line     The line to parse.
   * @param incoming Whether the transaction is incoming or outgoing.
   * @return The parsed transaction.
   * @throws IllegalArgumentException If the line is not a transaction
   */
  private Transaction parseTransaction(String line, boolean incoming) {
    //TODO parse the transaction so that incoming is not always false
    log.debug("Parsing transaction: " + line);
    String[] splitLine = line.trim().split("\\s+");

    int archiveReference = Integer.parseInt(splitLine[splitLine.length - 1]);

    if (archiveReference < 100000000) {
      throw new IllegalArgumentException("Archive reference too low, was: " + archiveReference);
    }

    Transaction transaction = new Transaction();
    try {
      String description = String.join(" ",
          Arrays.copyOfRange(splitLine, 2, splitLine.length - 2));
      transaction.setDescription(description);
    } catch (Exception e) {
      log.info("Error while parsing description: " + e.getMessage());
    }
    try {
      MonthDay date = MonthDay.parse(splitLine[splitLine.length - 2], formatter);
      transaction.setDate(date);
    } catch (Exception e) {
      log.info("Error while parsing date: " + e.getMessage());
    }

    try {
      Double amount = Double.parseDouble(splitLine[splitLine.length - 3]
          .replaceAll("\\.", "")
          .replaceAll(",", ".")
      );
      transaction.setAmount(amount);
    } catch (Exception e) {
      log.info("Error while parsing amount: " + e.getMessage());
    }

    transaction.setIsIncoming(incoming);
    return transaction;
  }
}
