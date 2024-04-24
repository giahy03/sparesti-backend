package edu.ntnu.idatt2106.sparesti.service.analysis.openai;

import static java.lang.Thread.sleep;

import edu.ntnu.idatt2106.sparesti.model.analysis.openai.OpenAiMessage;
import edu.ntnu.idatt2106.sparesti.model.analysis.openai.OpenAiPollResponse;
import edu.ntnu.idatt2106.sparesti.model.analysis.openai.OpenAiRunRequest;
import edu.ntnu.idatt2106.sparesti.model.analysis.openai.OpenAiRunRequestMessages;
import edu.ntnu.idatt2106.sparesti.model.analysis.openai.OpenAiThreadResponse;
import edu.ntnu.idatt2106.sparesti.model.analysis.openai.OpenAiThreadResponseList;
import io.github.cdimascio.dotenv.Dotenv;
import jakarta.validation.constraints.NotBlank;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Service class for sending and retrieving messages from the OpenAI API.
 */
@Slf4j
@Service
@AllArgsConstructor
public class OpenAiService {

  private static final int MAX_POLLING_ATTEMPTS = 60;
  public static final int POLLING_INTERVAL = 500;
  private static final String secretKey = Dotenv.load().get("OPENAI_API_KEY");

  public static final String OPENAI_API_URL = "https://api.openai.com/v1";

  private final RestTemplate restTemplate = new RestTemplate();

  /**
   * Send a message to the OpenAI API and get the response.
   *
   * @param message     the message to send
   * @param assistantId the assistant id to send the message to
   * @return the response from the OpenAI API
   * @throws SocketTimeoutException    if the thread takes too long to complete
   * @throws IndexOutOfBoundsException if the response is empty
   */
  public String sendMessage(String message, String assistantId)
      throws SocketTimeoutException, IndexOutOfBoundsException {


    OpenAiThreadResponse threadResponse = createThreadAndSendMessage(message, assistantId);

    try {
      pollResponse(threadResponse);
    } catch (SocketTimeoutException e) {
      log.error("Thread took too long to complete", e);
      throw new SocketTimeoutException("OpenAI api call took too long to complete");
    }
    OpenAiThreadResponseList openAiThreadResponseList =
        getResponseMessages(threadResponse.getThreadId());
    return openAiThreadResponseList.getData().stream()
        .filter(thread -> !thread.getRole().equals("user"))
        .toList().getFirst().getContent().getFirst().getText().getValue();
  }


  /**
   * Poll the OpenAI API for a response.
   *
   * @param threadResponse the thread response to poll
   * @throws SocketTimeoutException if the thread takes too long to complete
   */
  private void pollResponse(OpenAiThreadResponse threadResponse) throws SocketTimeoutException {
    int attemptCounter = 0;
    boolean isFinished = false;
    while (!isFinished) {
      try {
        sleep(POLLING_INTERVAL);
        OpenAiPollResponse pollResponse = pollRequest(threadResponse);
        if (pollResponse.getStatus().equals("completed")) {
          log.info("Thread is complete");
          isFinished = true;
        }

        attemptCounter++;
        if (attemptCounter > MAX_POLLING_ATTEMPTS) {
          log.error("Thread took too long to complete");
          throw new SocketTimeoutException("OpenAI api call took too long to complete");
        }
      } catch (InterruptedException e) {
        log.error("Thread interrupted", e);
      }
    }
  }

  /**
   * Get the response message from the OpenAI API.
   *
   * @param threadId the thread id to get the response message from
   */
  private OpenAiThreadResponseList getResponseMessages(String threadId) {
    final String formattedLocation =
        String.format(OPENAI_API_URL + "/threads/%s/messages", threadId);

    HttpEntity<Object> requestEntity = new HttpEntity<>(getHeaders());

    ResponseEntity<OpenAiThreadResponseList> responseEntity =
        restTemplate.exchange(formattedLocation, HttpMethod.GET, requestEntity,
            OpenAiThreadResponseList.class);


    if (responseEntity.getStatusCode().is2xxSuccessful()) {
      log.info(responseEntity.getBody() == null
          ? "retrieved message" + responseEntity.getBody().toString() : "Response is null");

      return responseEntity.getBody();

    } else {
      throw new RuntimeException("Failed to retrieve messages from OpenAI API");
    }
  }

  /**
   * Poll the OpenAI API for a response.
   *
   * @param openAiThreadResponse the thread response to poll
   * @return the response body from the OpenAI API
   */
  private OpenAiPollResponse pollRequest(@NonNull OpenAiThreadResponse openAiThreadResponse) {
    final String formattedLocation =
        String.format(OPENAI_API_URL + "/threads/%s/runs/%s", openAiThreadResponse.getThreadId(),
            openAiThreadResponse.getId());


    HttpEntity<Object> requestEntity = new HttpEntity<>(getHeaders());

    ResponseEntity<OpenAiPollResponse> responseEntity =
        restTemplate.exchange(formattedLocation, HttpMethod.GET, requestEntity,
            OpenAiPollResponse.class);

    if (responseEntity.getStatusCode().is2xxSuccessful()) {
      return responseEntity.getBody();
    } else {
      throw new RuntimeException("Failed to retrieve data from OpenAI API");
    }
  }

  /**
   * Creates a thread and sends a message to the OpenAI API.
   *
   * @param message the message to send
   * @return the response from the OpenAI API
   * @throws RuntimeException if the request fails
   */
  private OpenAiThreadResponse createThreadAndSendMessage(@NonNull @NotBlank String message,
                                                          @NonNull @NotBlank String assistantId)
      throws RuntimeException {


    log.info("Sending message from OpenAI");

    final String formattedLocation = OPENAI_API_URL + "/threads/runs";

    List<OpenAiMessage> messages = new ArrayList<>();
    messages.add(new OpenAiMessage("user", message));

    OpenAiRunRequestMessages openAiRunRequestMessages = new OpenAiRunRequestMessages(messages);
    OpenAiRunRequest openAiRunRequest = new OpenAiRunRequest(assistantId, openAiRunRequestMessages);

    HttpEntity<OpenAiRunRequest> requestEntity = new HttpEntity<>(openAiRunRequest, getHeaders());

    log.info("Sending message to OpenAI" + requestEntity.getBody().toString());
    log.info("Secret key" + secretKey);
    ResponseEntity<OpenAiThreadResponse> responseEntity =
        restTemplate.exchange(formattedLocation,
            HttpMethod.POST,
            requestEntity,
            OpenAiThreadResponse.class);

    if (responseEntity.getStatusCode().is2xxSuccessful()) {
      OpenAiThreadResponse response = responseEntity.getBody();
      if (response == null) {
        throw new RuntimeException("Failed to retrieve data from OpenAI API");
      } else {
        log.info("Open AI thread created with id: " + response.getThreadId());
      }
      return response;
    } else {
      throw new RuntimeException("Failed to retrieve data from OpenAI API");
    }

  }


  /**
   * Get the default Open AI API headers for the request.
   *
   * @return HttpHeaders
   */
  private @NonNull HttpHeaders getHeaders() {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.setBearerAuth(secretKey);
    headers.set("OpenAI-Beta", "assistants=v2");
    return headers;
  }
}

