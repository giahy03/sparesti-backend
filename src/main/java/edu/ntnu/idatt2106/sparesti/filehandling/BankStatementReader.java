package edu.ntnu.idatt2106.sparesti.filehandling;

import edu.ntnu.idatt2106.sparesti.model.banking.BankStatement;
import java.io.File;
import java.util.ArrayList;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.data.util.Pair;

/**
 * Class for reading bank statements from different banks.
 *
 * @author Tobias Oftedal
 */
@Slf4j
public abstract class BankStatementReader {
  public abstract void readFirstPage(String pageText, BankStatement bankStatement);

  public abstract void readStandardPage(String pageText, BankStatement bankStatement);

  /**
   * Reads a bank statement from SpareBank1. And creates a {@link BankStatement} object.
   *
   * @param file The file to read.
   * @return The parsed bank statement.
   */
  public BankStatement readStatement(File file) {

    try (PDDocument document = Loader.loadPDF(file)) {
      BankStatement bankStatement = new BankStatement();
      bankStatement.setTransactions(new ArrayList<>());

      String firstPageText = readPageToText(1, file);
      log.info("First page text: {}", firstPageText);
      readFirstPage(firstPageText, bankStatement);

      for (int i = 2; i < document.getNumberOfPages() + 1; i++) {
        String pageText = readPageToText(i, file);
        readStandardPage(pageText, bankStatement);
      }

      log.info("finalised reading statement from file:{}", file.getName());

      if (bankStatement.getTransactions().isEmpty()) {
        throw new IllegalArgumentException(
            "File is invalid, no transactions found in file:" + file);
      }

      return bankStatement;
    } catch (Exception e) {
      log.error("Error reading bank statement from file: {}", file.toString(), e);
      throw new IllegalArgumentException("Error reading SpareBank1 statement from file:" + file);
    }
  }

  /**
   * Reads a page from a pdf file and returns the text.
   *
   * @param pageNumber The page number to read.
   * @param file       The file to read.
   * @return The text of the page.
   */
  public String readPageToText(int pageNumber, File file) {
    try (PDDocument document = Loader.loadPDF(file)) {
      PDFTextStripper stripper = new PDFTextStripper();
      stripper.setStartPage(pageNumber);
      stripper.setEndPage(pageNumber);
      return stripper.getText(document).replace("\r", "");
    } catch (Exception e) {
      throw new IllegalArgumentException("Invalid pdf file");
    }
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
  protected Pair<Optional<String>, Integer> skipUntilFind(String textToContain, String[] splitText,
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
