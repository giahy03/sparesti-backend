package edu.ntnu.idatt2106.sparesti.service.news;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import edu.ntnu.idatt2106.sparesti.dto.NewsDto;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class NewsScrapingService {


    public List<NewsDto> scrapeDN() {
        List<NewsDto> newsList = new ArrayList<>();
        try {
            String url = "https://www.dn.no";
            Document doc = Jsoup.connect(url).get(); 
            Elements newsArticles = doc.select("article[data-articleid]"); 

            for (Element article : newsArticles) {
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
        } catch (IOException e) {
            e.printStackTrace();
        }
        return newsList; // Consider also what to return or how to handle errors if the try block fails
    }
}
