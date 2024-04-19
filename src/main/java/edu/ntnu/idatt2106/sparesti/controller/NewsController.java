package edu.ntnu.idatt2106.sparesti.controller;

import edu.ntnu.idatt2106.sparesti.dto.NewsDto;
import edu.ntnu.idatt2106.sparesti.service.email.EmailServiceImpl;
import edu.ntnu.idatt2106.sparesti.service.news.NewsScrapingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller class for getting news.
 *
 * @author Olav Sie
 * @version 1.0
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/news")
public class NewsController {

  private final NewsScrapingService newsService;

    /**
   * REST-endpoint to fetch news.
   * If news are fetched sucessfully, the news will be returned.
   *
   * @return ResponseEntity containing a list of DTO's with news.
   */
  @Operation(summary = "Fetching news")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "400", description = "Invalid input from user."),
          @ApiResponse(responseCode = "500", description = "Internal server error.")
  })
  @GetMapping("/news")
  public ResponseEntity<List<NewsDto>> getNews() {
    List<NewsDto> newsEntries = newsService.scrapeDN();
    log.info("Attempting to send news: {}", newsEntries);
    return ResponseEntity.ok(newsEntries);
  }
}
