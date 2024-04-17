package edu.ntnu.idatt2106.sparesti.service.analysis;

import edu.ntnu.idatt2106.sparesti.model.analysis.SsbPurchaseCategory;
import edu.ntnu.idatt2106.sparesti.model.banking.Transaction;
import java.util.Optional;
import org.springframework.stereotype.Service;

/**
 * Classifies a transaction into a category.
 */
@Service
public class TransactionService {
  /**
   * Categorizes a transaction.
   *
   * @param transaction the transaction to categorize.
   */
  public void categorize(Transaction transaction) {
    Optional<SsbPurchaseCategory> category = checkIfFood(transaction);
    category.ifPresent(transaction::setCategory);
  }

  /**
   * Checks if a transaction is a food purchase, and categorizes it if so.
   *
   * @param transaction the transaction to check.
   * @return the category of the transaction.
   */
  public Optional<SsbPurchaseCategory> checkIfFood(Transaction transaction) {
    String[] foodStores = {"rema", "kiwi", "meny", "joker", "bunnpris", "coop", "ica", "spar",
        "kiwi"};
    String lowercaseDescription = transaction.getDescription().toLowerCase();
    for (String store : foodStores) {
      if (lowercaseDescription.contains(store)) {
        return Optional.of(SsbPurchaseCategory.FOOD);
      }
    }
    return Optional.of(SsbPurchaseCategory.OTHER);
  }
}
