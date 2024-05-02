package edu.ntnu.idatt2106.sparesti.dto.analysis;

import static org.junit.jupiter.api.Assertions.assertEquals;

import edu.ntnu.idatt2106.sparesti.model.analysis.ssb.SsbPurchaseCategory;
import java.time.MonthDay;
import org.junit.jupiter.api.Test;

class TransactionDtoTest {
  @Test
  void givenValidInput_settersAndGetters_work(){
    //arrange
    long transactionId = 1;
    MonthDay date = MonthDay.of(1, 1);
    String description = "description";
    double amount = 1.0;
    boolean incoming = true;
    SsbPurchaseCategory category = SsbPurchaseCategory.FOOD;

    TransactionDto transactionDto = TransactionDto.builder().build();

    //act
    transactionDto.setId(transactionId);
    transactionDto.setDate(date);
    transactionDto.setDescription(description);
    transactionDto.setAmount(amount);
    transactionDto.setIsIncoming(incoming);
    transactionDto.setCategory(category);

    //assert

    assertEquals(transactionDto.getId(), transactionId);
    assertEquals(transactionDto.getDate(), date);
    assertEquals(transactionDto.getDescription(), description);
    assertEquals(transactionDto.getAmount(), amount);
    assertEquals(transactionDto.getIsIncoming(), incoming);
    assertEquals(transactionDto.getCategory(), category);

  }
}
