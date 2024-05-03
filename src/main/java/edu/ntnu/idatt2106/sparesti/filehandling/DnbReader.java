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
 * Reader for dnb {@link BankStatement bank statements}.
 *
 * @author Tobias Offtedal
 */
@Slf4j
public class DnbReader extends BankStatementReader {
  private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy");
  private static final String[] INCOMING_KEYWORDS = {"kontoregulering", "lønn"};

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

    String[] accountLineSplit = accountLine
        .getFirst()
        .orElseThrow()
        .split("\\s+");

    bankStatement.setAccountNumber(accountLineSplit[2]);
    Optional<String> accountName = getAccountName(accountLineSplit);
    if (accountName.isPresent()) {
      bankStatement.setAccountName(accountName.get());
    } else {
      bankStatement.setAccountName("Unknown");
    }

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

    StringBuilder currentString = new StringBuilder();
    for (; lineIndex < splitText.length; lineIndex++) {

      String line = splitText[lineIndex];

      currentString.append(" ").append(line);

      if (currentString.toString().isBlank()) {
        continue;
      }
      try {
        Transaction parsedTransaction = parseTransaction(currentString.toString());

        bankStatement.getTransactions().add(parsedTransaction);

        currentString = new StringBuilder();
      } catch (Exception e) {
        //If this line is reached, the line is not a transaction, but this error
        // is ignored as it is expected to happen
      }
    }
  }

  /**
   * Parses a transaction from a line in the DNB pdf. If a part of the transaction is not
   * valid, the method will set it to a default value.
   *
   * @param line The line to parse.
   * @return The parsed transaction.
   * @throws IllegalArgumentException If the line is not a transaction
   */
  private Transaction parseTransaction(String line) {
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
      transaction.setDescription("Unknown");
    }
    try {
      MonthDay date = MonthDay.parse(splitLine[splitLine.length - 2], formatter);
      transaction.setDate(date);
    } catch (Exception e) {
      transaction.setDate(MonthDay.now());
    }
    try {
      Double amount = Double.parseDouble(splitLine[splitLine.length - 3]
          .replace("\\.", "")
          .replace(",", ".")
      );
      transaction.setAmount(amount);
    } catch (Exception e) {
      transaction.setAmount(0.0);
    }

    try {
      for (String word : splitLine){
        if (Arrays.asList(INCOMING_KEYWORDS).contains(word.toLowerCase())){
          transaction.setIsIncoming(true);
          break;
        }
        transaction.setIsIncoming(false);
      }
    } catch (Exception e) {
      transaction.setIsIncoming(false);
    }

    return transaction;
  }

  /**
   * Gets the account name from the line containing the account number and date.
   *
   * @param accountAndDateLineSplit The split account and date line.
   * @return The account name.
   */
  private Optional<String> getAccountName(String[] accountAndDateLineSplit) {
    for (int i = accountAndDateLineSplit.length; i > 0; i--) {
      try {
        String[] possibleDates = accountAndDateLineSplit[i].split("\\.");
        for (String possibleDate : possibleDates) {
          Integer.parseInt(possibleDate);
        }
        return Optional.of(String.join(" ", Arrays.copyOfRange(accountAndDateLineSplit, i + 1,
            accountAndDateLineSplit.length)));
      } catch (Exception e) {
        //If this line is reached, the account name has not been completely read yet
      }
    }
    return Optional.empty();
  }
}
