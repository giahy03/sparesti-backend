package edu.ntnu.idatt2106.sparesti.service.news;

import edu.ntnu.idatt2106.sparesti.dto.NewsDto;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;


/**
 * Service for scraping news from different news sources.
 *
 * @author Olav Sie Rotvær
 * @version 1.0
 */
@Service
public class NewsScrapingService {


  /**
   * Scrapes news from the news site "Dagens Næringsliv".
   *
   * @return a list of news articles.
   */
  public List<NewsDto> scrapeDN(int page, int size) {

    List<NewsDto> newsList = new ArrayList<>();
    String url = "https://www.dn.no";
    Document doc = null;

    try {
      doc = Jsoup.connect(url).get();
    } catch (IOException e) {
      throw new RuntimeException("Failed to connect to " + url);
    }

    Elements newsArticles = doc.select("article[data-articleid]");

    int start = page * size;
    int end = Math.min(start + size, newsArticles.size());

    for (int i = start; i < end; i++) {
      Element article = newsArticles.get(i);
      String title = article.select(".dre-item__title p").text();
      String imageUrl = article.select(".dre-item__asset picture source").attr("srcset").split(",")[0].split(" ")[0];
      String articleUrl = url + article.select(".dre-item__asset").attr("href");
      String category = article.select(".dre-item__footer a").text();

      NewsDto newsDto = new NewsDto();
      newsDto.setTitle(title);
      newsDto.setImageUrl(imageUrl);
      newsDto.setArticleUrl(articleUrl);
      newsDto.setCategory(category);
      newsList.add(newsDto);
    }
    return newsList;
  }

}
