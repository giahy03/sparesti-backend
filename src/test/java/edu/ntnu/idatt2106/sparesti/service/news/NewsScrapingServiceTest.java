package edu.ntnu.idatt2106.sparesti.service.news;

import edu.ntnu.idatt2106.sparesti.dto.NewsDto;
import java.io.IOException;
import java.util.List;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Test class for NewsScrapingService.
 *
 * @version 1.0
 * @author Jeffrey Yaw Annor Tabiri
 */
@ExtendWith(MockitoExtension.class)
class NewsScrapingServiceTest {

  @InjectMocks
  private NewsScrapingService newsScrapingService;

  @Mock
  Document doc;
  @Mock
  Elements elements;

  @Test
  void Service_ScrapeDN_ReturnsNewsDtoList() throws IOException {

    // Arrange
    mockStatic(Jsoup.class);
    when(Jsoup.connect(anyString())).thenReturn(mock(Connection.class));
    when(Jsoup.connect(anyString()).get()).thenReturn(doc);
    when(doc.select(anyString())).thenReturn(elements);
    when(elements.size()).thenReturn(3);
    when(elements.get(0)).thenReturn(mock(Element.class));
    when(elements.get(0).select(anyString())).thenReturn(elements);
    when(elements.get(0).select(anyString()).text()).thenReturn("Title");
    when(elements.get(0).select(anyString()).attr(anyString())).thenReturn("https://www.dn.no");
    when(elements.get(0).select(anyString()).attr(anyString()).split(",")[0].split(" ")[0]).thenReturn("https://www.dn.no");
    when(elements.get(0).select(anyString()).attr(anyString())).thenReturn("https://www.dn.no");
    when(elements.get(0).select(anyString()).text()).thenReturn("Category");

    // Act
    List<NewsDto> newsDtoList = newsScrapingService.scrapeDn(0, 1);

    // Assert
    assertEquals(1, newsDtoList.size());

    when(Jsoup.connect(anyString())).thenReturn(mock(Connection.class));
    when(Jsoup.connect(anyString()).get()).thenThrow(new IOException());
    assertThrows(RuntimeException.class, () -> newsScrapingService.scrapeDn(1, 3));
  }
}