package edu.ntnu.idatt2106.sparesti.controller;

import edu.ntnu.idatt2106.sparesti.repository.user.UserRepository;
import edu.ntnu.idatt2106.sparesti.service.auth.JwtService;
import edu.ntnu.idatt2106.sparesti.service.news.NewsScrapingService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * Test class for the news controller.
 *
 * @author Hanne-Sofie Søreide
 */
@ExtendWith(MockitoExtension.class)
@WebMvcTest(NewsController.class)
public class NewsControllerTest {

    @InjectMocks
    private NewsController newsController;

    @MockBean
    NewsScrapingService newsScrapingService;
    @MockBean
    private JwtService jwtService;
    @MockBean
    private UserRepository userRepository;

    @Autowired
    private MockMvc mockMvc;

    String url = "/api/v1/news";

    @DisplayName("Test that controller is initialized.")
    @Test
    public void controllerIsInitialized() {

        assertThat(newsController).isNotNull();
    }


}
