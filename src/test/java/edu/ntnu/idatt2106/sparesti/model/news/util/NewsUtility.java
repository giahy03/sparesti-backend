package edu.ntnu.idatt2106.sparesti.model.news.util;

import edu.ntnu.idatt2106.sparesti.dto.NewsDto;

/**
 * Utility class to support tests of news.
 *
 * @author Hanne-Sofie Søreide
 */
public class NewsUtility {

  /**
   * Creates a news dto object for testing.
   *
   * @return a news dto object
   */
  public static String createNewsDtoJson() {

    return "["
            + "{"
            + "\"title\":\"Breaking News!\","
            + "\"category\":\"Sport\","
            + "\"imageUrl\":\"http-image-url\","
            + "\"articleUrl\":\"http-article-url\""
            + "},"
            + "{"
            + "\"title\":\"Also breaking news!\","
            + "\"category\":\"Not sport\","
            + "\"imageUrl\":\"http-image-url\","
            + "\"articleUrl\":\"http-article-url\""
            + "}"
            + "]";
  }

  /**
   * Creates a news dto object for testing.
   *
   * @return a news dto object
   */
  public static NewsDto createNewsDtoA() {
    NewsDto newsDto = new NewsDto("Breaking News!");
    newsDto.setCategory("Sport");
    newsDto.setImageUrl("http-image-url");
    newsDto.setArticleUrl("http-article-url");
    return newsDto;
  }

  /**
   * Creates a news dto object for testing.
   *
   * @return a news dto object
   */
  public static NewsDto createNewsDtoB() {
    NewsDto newsDto = new NewsDto("Also breaking news!");
    newsDto.setCategory("Not sport");
    newsDto.setImageUrl("http-image-url");
    newsDto.setArticleUrl("http-article-url");
    return newsDto;
  }

}
