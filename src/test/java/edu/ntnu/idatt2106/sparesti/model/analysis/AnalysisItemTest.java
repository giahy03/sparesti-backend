package edu.ntnu.idatt2106.sparesti.model.analysis;

import static org.junit.jupiter.api.Assertions.assertEquals;

import edu.ntnu.idatt2106.sparesti.model.analysis.ssb.SsbPurchaseCategory;
import org.junit.jupiter.api.Test;

public class AnalysisItemTest {

  @Test
  void givenValidParameters_whenConstructedWithAllArgsConstructor_thenObjectCreatedSuccessfully() {
    AnalysisItem analysisItem = new AnalysisItem();
    analysisItem.setActualValue(100.0);
    analysisItem.setExpectedValue(200.0);
    analysisItem.setPurchaseCategory(SsbPurchaseCategory.FOOD);

    AnalysisItem analysisItem2 = new AnalysisItem(SsbPurchaseCategory.FOOD, 200.0, 100.0);

    assertEquals(analysisItem, analysisItem2);
  }
}
