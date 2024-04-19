package edu.ntnu.idatt2106.sparesti.model.analysis.openai;

import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * Represents a request to the OpenAI API for a thread of messages.
 */
@RequiredArgsConstructor
@Data
@NoArgsConstructor
public class OpenAiRunRequestMessages {
  @NonNull
  private List<OpenAiMessage> messages;
}
