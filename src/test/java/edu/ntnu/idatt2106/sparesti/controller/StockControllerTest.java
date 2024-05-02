package edu.ntnu.idatt2106.sparesti.controller;

import edu.ntnu.idatt2106.sparesti.model.news.util.NewsUtility;
import edu.ntnu.idatt2106.sparesti.model.stock.util.StockUtility;
import edu.ntnu.idatt2106.sparesti.repository.user.UserRepository;
import edu.ntnu.idatt2106.sparesti.service.auth.JwtService;
import edu.ntnu.idatt2106.sparesti.service.news.NewsScrapingService;
import edu.ntnu.idatt2106.sparesti.service.stock.StockService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
@WebMvcTest(StockController.class)
public class StockControllerTest {


        @InjectMocks
        private StockController stocksController;

        @MockBean
        StockService stockService;
        @MockBean
        private JwtService jwtService;
        @MockBean
        private UserRepository userRepository;

        @Autowired
        private MockMvc mockMvc;

        String url = "/api/v1/stock";

        @DisplayName("Test that controller is initialized.")
        @Test
        public void controllerIsInitialized() {
            assertThat(stocksController).isNotNull();
        }


        @DisplayName("Test retrieving stock data")
        @Test
        @WithMockUser(roles = "USER")
        public void controller_getStock() throws Exception {

                when(stockService.getStockData("APPL")).thenReturn(StockUtility.createStockDataDto());

                mockMvc
                        .perform(get(url + "/APPL").with(csrf()))
                        .andExpect(status().isOk())
                        .andExpect(content().json(StockUtility.createStockDataDtoJson()));
        }



    }
