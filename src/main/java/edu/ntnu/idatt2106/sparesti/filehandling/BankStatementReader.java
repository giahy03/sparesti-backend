package edu.ntnu.idatt2106.sparesti.filehandling;

import edu.ntnu.idatt2106.sparesti.model.banking.BankStatement;
import edu.ntnu.idatt2106.sparesti.model.banking.Transaction;
import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

/**
 * Class for reading bank statements from different banks.
 */
@Slf4j
public abstract class BankStatementReader {
    public abstract BankStatement readStatement(Path fileLocation);

    /**
     * Just for testing purposes. Should not be used in production.
     * @param fileLocation The location of the file to read.
     */
    public void logStatementFully(Path fileLocation) {
        try (PDDocument document = Loader.loadPDF(new File(fileLocation.toString()))) {
            log.info("Reading SpareBank1 statement from file:" + fileLocation.toString());
            List<Transaction> transactions = new ArrayList<>();

            String text = new PDFTextStripper().getText(document);
            String[] splitText = text.split("\n");
            for (int i = 0; i < splitText.length; i++) {
                log.info("i: " + i + ", " + splitText[i]);
            }
        } catch (Exception e) {
            log.error("Error reading SpareBank1 statement from file:" + fileLocation.toString(), e);
        }
    }
}
