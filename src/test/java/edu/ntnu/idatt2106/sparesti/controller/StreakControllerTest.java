package edu.ntnu.idatt2106.sparesti.controller;


import edu.ntnu.idatt2106.sparesti.model.streak.StreakUtility;
import edu.ntnu.idatt2106.sparesti.model.user.util.UserUtility;
import edu.ntnu.idatt2106.sparesti.repository.user.UserRepository;
import edu.ntnu.idatt2106.sparesti.service.auth.JwtService;
import edu.ntnu.idatt2106.sparesti.service.streak.StreakService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test class for the Streak controller.
 *
 * @author Hanne-Sofie Søreide
 */

@ExtendWith(MockitoExtension.class)
@WebMvcTest(StreakController.class)
public class StreakControllerTest {

    @InjectMocks
    StreakController streakController;

    @MockBean
    StreakService streakService;
    @MockBean
    private JwtService jwtService;
    @MockBean
    private UserRepository userRepository;

    @Autowired
    private MockMvc mockMvc;

    String url = "/api/v1/streak";


    @DisplayName("Test that controller is initialized.")
    @Test
    public void controllerIsInitialized() {
        assertThat(streakController).isNotNull();
    }

    @DisplayName("Test change user streak")
    @Test
    @WithMockUser(password = "abc", username = "abc@email.com", roles = "USER")
    public void controller_changeUserStreak() throws Exception {

        mockMvc
                .perform(put(url).with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(StreakUtility.createStreakRequestDtoJson()))
                .andExpect(status().isOk());
    }


    @DisplayName("Test retrieve streak")
    @Test
    @WithMockUser(password = "abc", username = "abc@email.com", roles = "USER")
    public void controller_getUserStreak() throws Exception {

        mockMvc
                .perform(get(url).with(csrf()))
                .andExpect(status().isOk());
    }

}
