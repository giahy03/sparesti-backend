package edu.ntnu.idatt2106.sparesti.model.analysis.openai;

import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * Represents a list of responses from the OpenAI API.
 */
@Data
@RequiredArgsConstructor
@NoArgsConstructor
public class OpenAiThreadResponseList {
  @NonNull
  private String object;
  @NonNull
  private List<OpenAiThreadResponse> data;
}

