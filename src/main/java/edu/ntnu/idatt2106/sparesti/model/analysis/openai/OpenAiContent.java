package edu.ntnu.idatt2106.sparesti.model.analysis.openai;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;


/**
 * Represents the content of a message in a thread.
 */
@Data
@RequiredArgsConstructor
@NoArgsConstructor
public class OpenAiContent {
  @NonNull
  private String type;
  @NonNull
  private OpenAiText text;


}
