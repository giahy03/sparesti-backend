package edu.ntnu.idatt2106.sparesti.dto.ssb.response;

import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * Represents the response from the SSB consumption API.
 */
@Data
@NoArgsConstructor
public class SsbConsumptionApiResponseDto {
  @NonNull
  private List<Double> value;
}
