package edu.ntnu.idatt2106.sparesti.dto.analysis;

import lombok.Data;

/**
 * Data transfer object for AnalysisItem.
 */
@Data
public class AnalysisItemDto {
  private Long id;
  private String category;
  private Double expectedValue;
  private Double actualValue;
}
