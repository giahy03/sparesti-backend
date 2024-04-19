package edu.ntnu.idatt2106.sparesti.dto;

import lombok.Data;
import lombok.NonNull;

/**
 * Data Transfer Object (DTO) containing news objects.
 *
 * @author Olav Sie
 * @version 1.0
 */
@Data
public class NewsDto {
  @NonNull
  private String title;
  private String category;
  private String imageUrl;
  private String articleUrl;
}
