package edu.ntnu.idatt2106.sparesti.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * Data Transfer Object (DTO) containing news objects.
 *
 * @author Olav Sie
 * @version 1.0
 */
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class NewsDto {
  @NonNull
  private String title;
  private String category;
  private String imageUrl;
  private String articleUrl;
}
