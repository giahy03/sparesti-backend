package edu.ntnu.idatt2106.sparesti.filehandling;

import edu.ntnu.idatt2106.sparesti.model.banking.BankStatement;
import edu.ntnu.idatt2106.sparesti.model.banking.Transaction;
import java.nio.file.Path;
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
    log.info("Reading transactions from SpareBank1 page 2");

    for (String line : splitText) {
      log.info(line);

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

    String statementYearMonthString = accountAndDateLineSplit[8];
    String[] yearMonthSplit = statementYearMonthString.split("\\.");

    bankStatement.setTimestamp(YearMonth.of(Integer.parseInt(yearMonthSplit[2]),
        Integer.parseInt(yearMonthSplit[1])));


    lineIndex = accountAndDateLine.getSecond();

    for (; lineIndex < splitText.length; lineIndex++) {
      if (splitText[lineIndex].toLowerCase().contains("saldo fra")) {
        lineIndex++;
        break;
      }
    }
    for (; lineIndex < splitText.length; lineIndex++) {
      String line = splitText[lineIndex];

      try {
        Transaction transaction;
        if (line.toLowerCase().contains("fra")) {
          transaction = parseTransaction(line, true);
        } else {
          transaction = parseTransaction(line, false);
        }
        bankStatement.getTransactions().add(transaction);
      } catch (Exception e) {
        break;
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

    String dateString = splitLine[splitLine.length - 1];
    String formatedDateString = dateString.substring(2) + "-" + dateString.substring(0, 2);
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd");
    MonthDay monthDay = MonthDay.parse(formatedDateString, formatter);

    String description = String.join(" ", Arrays.copyOfRange(splitLine, 0, splitLine.length - 2));
    double amount = Double.parseDouble(
        splitLine[splitLine.length - 2].replaceAll("\\.", "").replaceAll(",", "."));

    return new Transaction(monthDay, description, amount, incoming);
  }


}
