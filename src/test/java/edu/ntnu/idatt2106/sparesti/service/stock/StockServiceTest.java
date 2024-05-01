package edu.ntnu.idatt2106.sparesti.service.stock;

import com.fasterxml.jackson.core.JsonProcessingException;
import edu.ntnu.idatt2106.sparesti.dto.stock.StockDataDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StockServiceTest {

  @Mock
  RestTemplate restTemplate;

  @InjectMocks
  StockService stockService;



  @Test
  void Service_GetStockData_ReturnsCorrectStockData() throws JsonProcessingException {

//    String symbol = "AAPL";
//    // Arrange
//    String jsonResponse = "{\"ticker\":\"AAPL\",\"queryCount\":1,\"resultsCount\":1,\"adjusted\":true,\"results\":[{\"v\":6.4066593e+07,\"vw\":172.1915,\"o\":173.33,\"c\":170.33,\"h\":174.99,\"l\":170,\"t\":1714449600000,\"n\":647398}],\"status\":\"DELAYED\",\"request_id\":\"d242686a107f50766796da8042f84622\",\"count\":1}";
//    when(restTemplate.getForObject(anyString(), eq(String.class))).thenReturn(jsonResponse);
//
//    // Act
//    StockDataDto actualStockDataDto = stockService.getStockData(symbol);
//    StockDataDto expectedStockDataDto = new StockDataDto(170.33, -1.73);
//
//    // Assert
//    assertEquals(expectedStockDataDto, actualStockDataDto);
//    assertEquals(expectedStockDataDto.getChangePercent(), actualStockDataDto.getChangePercent());
  }

}