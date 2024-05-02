package edu.ntnu.idatt2106.sparesti.dto.analysis;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class AnalysisItemDtoTest {

  @Test
  void givenValidInput_gettersAndSetters_work() {

    //arrange
    AnalysisItemDto analysisItemDto = new AnalysisItemDto();
    Long id = 1L;
    String category = "category";
    Double expectedValue = 1.0;
    Double actualValue = 1.0;

    //act
    analysisItemDto.setId(id);
    analysisItemDto.setCategory(category);
    analysisItemDto.setExpectedValue(expectedValue);
    analysisItemDto.setActualValue(actualValue);

    //assert
    assertEquals(id, analysisItemDto.getId());
    assertEquals(category, analysisItemDto.getCategory());
    assertEquals(expectedValue, analysisItemDto.getExpectedValue());
    assertEquals(actualValue, analysisItemDto.getActualValue());

  }

}
