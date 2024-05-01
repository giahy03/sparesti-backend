package edu.ntnu.idatt2106.sparesti.service.stock;

import edu.ntnu.idatt2106.sparesti.dto.stock.StockDataDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StockServiceTest {

  @InjectMocks
  StockService stockService;

  @Mock
  RestTemplate restTemplate;

  @Test
  void Service_GetStockData_ReturnsCorrectStockData() {
    //Arrange
  }
}