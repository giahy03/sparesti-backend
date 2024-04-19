package edu.ntnu.idatt2106.sparesti.model.analysis.openai;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * Represents the response from polling the OpenAI API.
 */
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class OpenAiPollResponse {
  @NonNull
  private String status;
}
