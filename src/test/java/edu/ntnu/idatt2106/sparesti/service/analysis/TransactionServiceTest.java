package edu.ntnu.idatt2106.sparesti.service.analysis;

import static org.junit.jupiter.api.Assertions.assertEquals;

import edu.ntnu.idatt2106.sparesti.model.analysis.SsbPurchaseCategory;
import edu.ntnu.idatt2106.sparesti.model.banking.Transaction;
import java.time.MonthDay;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)

public class TransactionServiceTest {

  @InjectMocks
  TransactionService transactionService;

  @Test
  public void givenFoodTransaction_whenCategorize_thenCategoryIsFood() {
    // Arrange
    Transaction transaction = new Transaction(MonthDay.now(), "kiwi", 100.0, false);

    // Act

    transactionService.categorize(transaction);

    // Assert
    SsbPurchaseCategory expected = SsbPurchaseCategory.FOOD;

    SsbPurchaseCategory actual = transaction.getCategory();

    assertEquals(expected, actual);
  }

  @Test
  public void givenUncategorizableTransaction_whenCategorize_thenCategoryIsOther() {
    // Arrange
    Transaction transaction = new Transaction(MonthDay.now(), "abc", 100.0, false);

    // Act

    transactionService.categorize(transaction);

    // Assert
    SsbPurchaseCategory expected = SsbPurchaseCategory.OTHER;

    SsbPurchaseCategory actual = transaction.getCategory();

    assertEquals(expected, actual);
  }
}
