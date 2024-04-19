package edu.ntnu.idatt2106.sparesti.service.news;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;

public class NewsScrapingService {

    public void scrapeDN() {
        try {
            String url = "https://www.dn.no";
            Document doc = Jsoup.connect(url).get(); 

            Elements newsArticles = doc.select("article[data-articleid]"); 

            for (Element article : newsArticles) {
                String title = article.select(".dre-item__title p").text(); 
                String imageUrl = article.select(".dre-item__asset picture source").attr("srcset").split(",")[0].split(" ")[0]; 
                String articleUrl = url + article.select(".dre-item__asset").attr("href"); 
                String category = article.select(".dre-item__footer a").text();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        NewsScrapingService service = new NewsScrapingService();
        service.scrapeDN();
    }
}
