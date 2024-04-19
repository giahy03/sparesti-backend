package edu.ntnu.idatt2106.sparesti.model.analysis.openai;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * Represents a message in a thread.
 */
@Data
@RequiredArgsConstructor
@NoArgsConstructor
public class OpenAiMessage {
  @NonNull
  private String role;
  @NonNull
  private String content;
}
