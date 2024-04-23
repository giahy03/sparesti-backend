package edu.ntnu.idatt2106.sparesti.filehandling;

import edu.ntnu.idatt2106.sparesti.model.banking.BankStatement;
import edu.ntnu.idatt2106.sparesti.model.banking.Transaction;
import java.io.File;
import java.nio.file.Path;
import java.time.MonthDay;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.data.util.Pair;

/**
 * Class for creating {@link BankStatement} objects from SpareBank1 pdf format.
 */
@Slf4j
public class SpareBank1Reader extends BankStatementReader {

  BankStatement readStatementFromPath(Path fileLocation) {
    log.info("Reading SpareBank1 statement from file:" + fileLocation.toString());

    return readStatement(fileLocation.toFile());

  }

  /**
   * Reads a bank statement from SpareBank1. And creates a {@link BankStatement} object.
   *
   * @param file The file to read.
   * @return The parsed bank statement.
   */
  @Override
  public BankStatement readStatement(File file) {

    try {

      List<Transaction> transactions = new ArrayList<>();
      String accountNumber;
      YearMonth yearMonth;

      PDDocument document = Loader.loadPDF(file);
      String text = new PDFTextStripper().getText(document);
      String[] splitText = text.split("\n");


      int lineIndex = 0;
      Pair<Optional<String>, Integer> accountAndDateLine =
          skipUntilFind("kontoutskrift", splitText, lineIndex);
      String[] accountAndDateLineSplit = accountAndDateLine.getFirst().orElseThrow().split(" ");
      accountNumber = accountAndDateLineSplit[5];

      String statementYearMonthString = accountAndDateLineSplit[8];
      String[] yearMonthSplit = statementYearMonthString.split("\\.");
      yearMonth =
          YearMonth.of(Integer.parseInt(yearMonthSplit[2]), Integer.parseInt(yearMonthSplit[1]));


      lineIndex = accountAndDateLine.getSecond();

      for (; lineIndex < splitText.length; lineIndex++) {
        if (splitText[lineIndex].toLowerCase().contains("saldo fra")) {
          lineIndex++;
          break;
        }
      }
      for (; lineIndex < splitText.length; lineIndex++) {
        String line = splitText[lineIndex];
        String[] splitLine = line.split(" ");

        try {
          //the transactions have ended
          Integer.parseInt(splitLine[splitLine.length - 1]);
        } catch (Exception e) {
          break;
        }
        Transaction transaction;
        if (line.toLowerCase().contains("fra")) {
          transaction = parseTransaction(line, true);
        } else {
          transaction = parseTransaction(line, false);
        }
        transactions.add(transaction);
      }
      log.info("finalised reading SpareBank1 statement from file:" + file.getName());
      return new BankStatement(accountNumber, transactions, yearMonth);
    } catch (Exception e) {
      log.error("Error reading SpareBank1 statement from file:" + file.toString(), e);
      return null;
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

  /**
   * Skips lines until it finds a line containing the textToContain.
   *
   * @param textToContain The text segment to find.
   * @param splitText     The text to search through.
   * @param lineIndex     The current index of the splitText.
   * @return A pair containing the found line and the index of the next line. If the line is not
   *        found the first element of the pair will be empty.
   */
  public Pair<Optional<String>, Integer> skipUntilFind(String textToContain, String[] splitText,
                                                       int lineIndex) {
    String foundLine = null;
    for (; lineIndex < splitText.length; lineIndex++) {
      if (splitText[lineIndex].toLowerCase().contains(textToContain)) {
        foundLine = splitText[lineIndex];
        lineIndex = lineIndex + 1;
        break;
      }
    }
    return Pair.of(Optional.ofNullable(foundLine), lineIndex);
  }
}
