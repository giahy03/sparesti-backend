package edu.ntnu.idatt2106.sparesti.model.stock.util;

import edu.ntnu.idatt2106.sparesti.dto.stock.StockDataDto;

/**
 * Utility class to support tests of stocks.
 *
 * @author Hanne-Sofie Søreide
 */
public class StockUtility {

  /**
   * Creates a json string of a stock data dto.
   *
   * @return a json string of a stock data dto.
   */
  public static String createStockDataDtoJson() {

    return "{"
            + "\"currentPrice\":110.0,"
            + "\"changePercent\":0.5"
            + "}";
  }


  /**
   * Creates a stock data dto object.
   *
   * @return a stock data dto object.
   */
  public static StockDataDto createStockDataDto() {
    return new StockDataDto(110.0, 0.5);
  }

}
