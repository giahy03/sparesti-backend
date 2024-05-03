package edu.ntnu.idatt2106.sparesti.service.stock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ntnu.idatt2106.sparesti.dto.stock.StockDataDto;
import edu.ntnu.idatt2106.sparesti.exception.stock.StockProcessingException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;


/**
 * Test class for the StockService class.
 *
 * @author Jeffrey Yaw Annor Tabiri
 * @version 1.0
 */
@ExtendWith(MockitoExtension.class)
class StockServiceTest {
  @Mock
  RestTemplate restTemplate;

  @Mock
  ObjectMapper objectMapper;

  @InjectMocks
  StockService stockService;


  @Test
  @DisplayName("Service get stock data should return correct stock data")
  void service_GetStockData_ReturnsCorrectStockData() throws JsonProcessingException {
    // Arrange
    String symbol = "AAPL";

    String jsonResponse = "{\"results\":[{\"c\":150.0},{\"c\":171.0}]}";
    when(restTemplate.getForObject(anyString(), eq(String.class))).thenReturn(jsonResponse);
    when(objectMapper.readTree(jsonResponse)).thenReturn(new ObjectMapper().readTree(jsonResponse));

    StockDataDto result = stockService.getStockData("AAPL");
    assertEquals(171.0, result.getCurrentPrice());
  }

  @Test
  void service_GetStockData_ReturnsException() throws JsonProcessingException {
    // Arrange
    String symbol = "AAPL";
    //empty results
    String jsonResponse = "{\"results\":[]}";

    when(restTemplate.getForObject(anyString(),
            eq(String.class))).thenThrow(new RuntimeException());
    assertThrows(RuntimeException.class, () -> stockService.getStockData(symbol));

    when(restTemplate.getForObject(anyString(), eq(String.class))).thenReturn("Invalid JSON");
    assertThrows(StockProcessingException.class, () -> stockService.getStockData(symbol));
  }

}