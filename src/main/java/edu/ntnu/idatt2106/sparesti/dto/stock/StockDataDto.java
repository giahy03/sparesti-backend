package edu.ntnu.idatt2106.sparesti.dto.stock;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Data Transfer Object (DTO) representing stock data.
 *
 * @author Ramtin Samavat
 * @version 1.0
 */
@Data
@AllArgsConstructor
public class StockDataDto {
  private double currentPrice;
  private double changePercent;
}
