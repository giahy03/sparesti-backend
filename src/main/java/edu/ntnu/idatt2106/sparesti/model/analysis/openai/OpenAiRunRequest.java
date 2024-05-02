package edu.ntnu.idatt2106.sparesti.model.analysis.openai;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * Represents a request to create and run a thread.
 */
@Data
@RequiredArgsConstructor
@NoArgsConstructor

public class OpenAiRunRequest {
  @NonNull
  @JsonProperty("assistant_id")
  private String assistantId;
  @NonNull
  private OpenAiRunRequestMessages thread;
}
