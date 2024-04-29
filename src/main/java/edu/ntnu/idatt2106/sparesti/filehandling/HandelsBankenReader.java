package edu.ntnu.idatt2106.sparesti.filehandling;

import edu.ntnu.idatt2106.sparesti.model.banking.BankStatement;
import edu.ntnu.idatt2106.sparesti.model.banking.Transaction;
import java.time.MonthDay;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.util.Pair;

/**
 * Reader for HandelsBanken bank pdf statements.
 */
@Slf4j
public class HandelsBankenReader extends BankStatementReader {


  @Override
  public void readStandardPage(String pageText, BankStatement bankStatement) {
    String[] splitText = pageText.split("\n");

    for (String line : splitText) {
      log.debug(line);

      String[] splitLine = line.split(" ");

      try {
        Integer.parseInt(splitLine[splitLine.length - 1]);
        Transaction transaction;
        if (line.toLowerCase().contains("fra")) {
          transaction = parseTransaction(line, true);
        } else {
          transaction = parseTransaction(line, false);
        }
        bankStatement.getTransactions().add(transaction);
      } catch (Exception e) {
        log.info(e.getMessage());
      }
    }
  }

  @Override
  public void readFirstPage(String pageText, BankStatement bankStatement) {
    String[] splitText = pageText.split("\n");

    int lineIndex = 0;
    Pair<Optional<String>, Integer> accountAndDateLine =
        skipUntilFind("kontoutskrift", splitText, lineIndex);
    String[] accountAndDateLineSplit = accountAndDateLine.getFirst().orElseThrow().split(" ");

    bankStatement.setAccountNumber(accountAndDateLineSplit[5]);

    Optional<String> accountName = getAccountName(accountAndDateLineSplit);
    if (accountName.isPresent()) {
      bankStatement.setAccountName(accountName.get());
    } else {
      bankStatement.setAccountName("Unknown");
      log.info("Account name not found");
    }

    String statementYearMonthString = accountAndDateLineSplit[8];
    String[] yearMonthSplit = statementYearMonthString.split("\\.");

    bankStatement.setTimestamp(YearMonth.of(Integer.parseInt(yearMonthSplit[2]),
        Integer.parseInt(yearMonthSplit[1])));


    lineIndex = accountAndDateLine.getSecond();

    for (; lineIndex < splitText.length; lineIndex++) {
      String line = splitText[lineIndex];

      log.info("Parsing line: {}", line);
      try {
        Transaction transaction;
        if (line.toLowerCase().contains("fra")) {
          transaction = parseTransaction(line, true);
        } else {
          transaction = parseTransaction(line, false);
        }
        bankStatement.getTransactions().add(transaction);
      } catch (Exception ignored) {

      }
    }
  }

  /**
   * Parses a transaction from a line in the SpareBank1 pdf.
   *
   * @param line     The line to parse.
   * @param incoming Whether the transaction is incoming or outgoing.
   * @return The parsed transaction.
   */
  private Transaction parseTransaction(String line, boolean incoming) {
    String[] splitLine = line.split(" ");

    log.info("Parsing transaction: {}", line);

    String dateString = splitLine[splitLine.length - 1];
    String formatedDateString = dateString.substring(2) + "-" + dateString.substring(0, 2);
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd");
    MonthDay monthDay = MonthDay.parse(formatedDateString, formatter);

    String description = String.join(" ", Arrays.copyOfRange(splitLine, 0, splitLine.length - 2));
    double amount = Double.parseDouble(
        splitLine[splitLine.length - 2]
            .replaceAll("\\.", "")
            .replaceAll(",", "."));

    return new Transaction(monthDay, description, amount, incoming);
  }

  /**
   * Gets the account name from the account and date line.
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
        String accountName = String.join(" ", Arrays.copyOfRange(accountAndDateLineSplit, i + 1,
            accountAndDateLineSplit.length));
        log.info("Account name is {}", accountName);
        return Optional.of(accountName);
      } catch (Exception e) {
        //If this line is reached, the account name has not been completely read yet
      }
    }
    return Optional.empty();
  }


}
