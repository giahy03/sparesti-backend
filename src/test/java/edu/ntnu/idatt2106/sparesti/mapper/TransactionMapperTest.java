package edu.ntnu.idatt2106.sparesti.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import edu.ntnu.idatt2106.sparesti.dto.analysis.TransactionDto;
import edu.ntnu.idatt2106.sparesti.model.analysis.ssb.SsbPurchaseCategory;
import edu.ntnu.idatt2106.sparesti.model.banking.BankStatement;
import edu.ntnu.idatt2106.sparesti.model.banking.Transaction;
import java.time.MonthDay;
import org.junit.jupiter.api.Test;

class TransactionMapperTest {
  @Test
  void givenTransaction_whenTransactionToTransactionDto_thenReturnValidTransaction() {
    // arrange
    MonthDay date = MonthDay.of(1, 1);
    String description = "description";
    Double amount = 1.0;
    Boolean isIncoming = true;
    SsbPurchaseCategory category = SsbPurchaseCategory.OTHER;
    BankStatement bankStatement = new BankStatement();


    Transaction transaction = new Transaction();
    transaction.setDate(date);
    transaction.setDescription(description);
    transaction.setAmount(amount);
    transaction.setIsIncoming(isIncoming);
    transaction.setCategory(category);
    transaction.setBankStatement(bankStatement);

    // act
    TransactionDto transactionDto =
        TransactionMapper.INSTANCE.transactionToTransactionDto(transaction);

    // assert
    assertEquals(date, transactionDto.getDate());
    assertEquals(description, transactionDto.getDescription());
    assertEquals(amount, transactionDto.getAmount());
    assertEquals(isIncoming, transactionDto.getIsIncoming());
    assertEquals(category, transactionDto.getCategory());
  }

  @Test
  void givenTransactionDto_whenTransactionDtoToTransaction_thenReturnValidTransaction() {
    // arrange
    MonthDay date = MonthDay.of(1, 1);
    String description = "description";
    Double amount = 1.0;
    Boolean isIncoming = true;
    SsbPurchaseCategory category = SsbPurchaseCategory.OTHER;

    TransactionDto transactionDto = TransactionDto.builder()
        .date(date)
        .description(description)
        .amount(amount)
        .isIncoming(isIncoming)
        .category(category)
        .build();

    // act
    Transaction transaction =
        TransactionMapper.INSTANCE.transactionDtoToTransaction(transactionDto);

    // assert
    assertEquals(date, transaction.getDate());
    assertEquals(description, transaction.getDescription());
    assertEquals(amount, transaction.getAmount());
    assertEquals(isIncoming, transaction.getIsIncoming());
    assertEquals(category, transaction.getCategory());
  }


}
