package edu.ntnu.idatt2106.sparesti.model.analysis.openai;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * Represents a thread response from the OpenAI API.
 */
@RequiredArgsConstructor
@Data
@NoArgsConstructor
public class OpenAiThreadResponse {
  @NonNull
  private String id;
  @NonNull
  private String object;
  @NonNull
  @JsonProperty("created_at")
  private String createdAt;
  @JsonProperty("assistant_id")
  private String assistantId;
  @NonNull
  @JsonProperty("thread_id")
  private String threadId;
  private List<OpenAiContent> content;
  private String role;
}
