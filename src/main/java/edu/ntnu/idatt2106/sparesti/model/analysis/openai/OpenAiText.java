package edu.ntnu.idatt2106.sparesti.model.analysis.openai;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * Represents a text value in a OPenAI message.
 */
@Data
@RequiredArgsConstructor
@NoArgsConstructor
public class OpenAiText {
  @NonNull
  private String value;
}
