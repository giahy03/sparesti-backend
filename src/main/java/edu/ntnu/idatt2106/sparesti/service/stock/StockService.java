package edu.ntnu.idatt2106.sparesti.service.stock;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ntnu.idatt2106.sparesti.dto.stock.StockDataDto;
import edu.ntnu.idatt2106.sparesti.exception.stock.StockNotFoundException;
import edu.ntnu.idatt2106.sparesti.exception.stock.StockProcessingException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * <p>
 * Service class for retrieving stock data from the Polygon API.
 * </p>
 *
 * <p>
 * The Polygon API provides access to stock market data. The API key and doc can be obtained
 * on the Polygon website: <a href="https://polygon.io">Polygon API</a>.
 * </p>
 *
 * <p>
 * Notes on data: This service uses the free version of the Polygon API, which has limitations on
 * obtaining live data. Therefore, the retrieved data is from the day before.
 * </p>
 *
 * @author Ramtin Samavat
 * @version 1.0
 */
@Slf4j
@Service
public class StockService {

  private static final String API_KEY = "pDXb7Jx7ATG42CHvttBBTuuq7g5AW4Gc";
  private static final String API_URL = "https://api.polygon.io/v2/aggs/ticker/%s/range/1/day/%s/%s?adjusted=true&sort=asc&limit=120&apiKey=%s";

  private final RestTemplate restTemplate = new RestTemplate();
  private final ObjectMapper objectMapper = new ObjectMapper();

  /**
   * Retrieves stock data for the specified symbol.
   *
   * @param symbol The symbol of the stock to retrieve data for.
   * @return StockDataDto containing the current price and change percentage.
   * @throws StockNotFoundException if stock data is not found for the specified symbol.
   * @throws StockProcessingException if an error occurs while processing the stock data.
   */
  public StockDataDto getStockData(String symbol) {

    LocalDate currentDate = LocalDate.now();
    DayOfWeek currentDayOfWeek = currentDate.getDayOfWeek();

    String startDate;
    String endDate;

    if (currentDayOfWeek == DayOfWeek.MONDAY || currentDayOfWeek == DayOfWeek.SUNDAY) {

      int daysToAdd = (currentDayOfWeek == DayOfWeek.SUNDAY) ? 1 : 2;

      startDate = currentDate.minusDays(daysToAdd + 2).toString();
      endDate = currentDate.minusDays(daysToAdd + 1).toString();

    } else if (currentDayOfWeek == DayOfWeek.TUESDAY) {
      startDate = currentDate.minusDays(4).toString();
      endDate = currentDate.minusDays(1).toString();
    } else {
      startDate = currentDate.minusDays(2).toString();
      endDate = currentDate.minusDays(1).toString();
    }

    try {
      String url = String.format(API_URL, symbol, startDate, endDate, API_KEY);
      String jsonResponse = restTemplate.getForObject(url, String.class);

      log.debug("JSON response from Polygon api: {}", jsonResponse);

      JsonNode rootNode = objectMapper.readTree(jsonResponse);
      JsonNode resultsNode = rootNode.get("results");

      if (resultsNode == null) {
        throw new StockNotFoundException("Stock data not found for symbol: " + symbol);
      }

      JsonNode latestData = resultsNode.get(1);
      double currentPrice = latestData.get("c").asDouble();

      JsonNode previousData = resultsNode.get(0);
      double previousPrice = previousData.get("c").asDouble();

      double changePercent = ((currentPrice - previousPrice) / previousPrice) * 100;

      changePercent = Double.parseDouble(String.format("%.2f", changePercent));
      currentPrice = Double.parseDouble(String.format("%.2f", currentPrice));

      return new StockDataDto(currentPrice, changePercent);

    } catch (StockNotFoundException e) {
      throw new StockNotFoundException(e.getMessage());
    } catch (Exception e) {
      throw new StockProcessingException("Failed to process stock data: " + e.getMessage(), e);
    }
  }
}