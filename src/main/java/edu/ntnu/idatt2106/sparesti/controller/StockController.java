package edu.ntnu.idatt2106.sparesti.controller;

import edu.ntnu.idatt2106.sparesti.dto.stock.StockDataDto;
import edu.ntnu.idatt2106.sparesti.service.stock.StockService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller class responsible for handling stock related REST-endpoints.
 *
 * @author Ramtin Samavat
 * @version 1.0
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/stock")
public class StockController {

  private final StockService stockService;

  /**
   * REST endpoint for retrieving stock data for a specified stock symbol.
   *
   * @param symbol The stock symbol for which to retrieve data.
   * @return A ResponseEntity containing stock data with status OK if the operation is successful.
   */
  @Operation(summary = "Retrieve stock data.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successfully retrieved stock data",
          content = {
              @Content(mediaType = "application/json",
                  schema = @Schema(implementation = StockDataDto.class))}
      ),
      @ApiResponse(responseCode = "404", description = "Stock symbol not found"),
      @ApiResponse(responseCode = "500", description = "Internal server error.")
  })
  @GetMapping("/{symbol}")
  public ResponseEntity<StockDataDto> getDailyStockData(@PathVariable String symbol) {
    log.info("Attempting to retrieve stock data for symbol {}", symbol);
    StockDataDto stockDataDto = stockService.getStockData(symbol);
    log.info("Stock data for symbol {} retrieved.", symbol);
    return new ResponseEntity<>(stockDataDto, HttpStatus.OK);
  }
}
