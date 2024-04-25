package edu.ntnu.idatt2106.sparesti.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import edu.ntnu.idatt2106.sparesti.dto.badge.BadgePreviewDto;
import edu.ntnu.idatt2106.sparesti.mapper.BadgeMapper;
import edu.ntnu.idatt2106.sparesti.model.badge.util.BadgeUtility;
import edu.ntnu.idatt2106.sparesti.repositories.user.UserRepository;
import edu.ntnu.idatt2106.sparesti.repository.AchievementRepository;
import edu.ntnu.idatt2106.sparesti.service.auth.JwtService;
import edu.ntnu.idatt2106.sparesti.service.badge.BadgeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;

import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.when;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;


/**
 * Test class for the badge controller.
 *
 * @author Hanne-Sofie Søredie
 */

@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = BadgeController.class)
public class BadgeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    BadgeController badgeController;

    @MockBean
    private BadgeService badgeService;

    @MockBean
    private UserRepository userRepository;
    @MockBean
    private AchievementRepository achievementRepository;
    @MockBean
    private JwtService jwtService;
    @MockBean
    private BadgeMapper badgeMapper;


    String url = "/api/v1/badge-manager/";

    @BeforeEach
    public void setUp() {
    }

    @DisplayName("Test that the controller is initialized.")
    @Test
    public void controllerInitializedCorrectly() {
        assertThat(badgeController).isNotNull();
    }

/*

    @DisplayName("Test that badge controller returns a badge correctly.")
    @Test
    @WithMockUser(roles = "USER")
    public void getBadgeByIdReturnsCorrectJson() throws Exception {

        when(badgeService.getBadgeById(any(BadgeIdDto.class))).thenReturn(BadgeUtility.createBadgePreviewDto());

        mockMvc
                .perform(get(url + "badge").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(BadgeUtility.createBadgeIdDtoJson()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(BadgeUtility.createBadgePreviewDtoJson()));
    }
*/



    @DisplayName("Test that badge controller returns all badges of user correctly.")
    @Test
    @WithMockUser(roles = "USER")
    public void getBadgesByEmailReturnsCorrectJson() throws Exception {

            BadgePreviewDto previewDto1 = BadgeUtility.createBadgePreviewDto();
            BadgePreviewDto previewDto2 = BadgeUtility.createBadgePreviewDto1();
            BadgePreviewDto previewDto3 = BadgeUtility.createBadgePreviewDto2();
            List<BadgePreviewDto> badges = Arrays.asList(previewDto1, previewDto2, previewDto3);

            when(badgeService.getAllBadgesByEmail(any(Principal.class),any(PageRequest.class))).thenReturn(badges);

            mockMvc
                .perform(get(url + "badges").with(csrf())
                        .param("page", "0")
                        .param("pageSize", "10"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(BadgeUtility.createBadgePreviewDtoListJson()));
    }

/*

    @DisplayName("Test that the badge controller responds correctly to delete request.")
    @Test
    @WithMockUser
    public void controllerDeletesBadgeCorrectly() throws Exception {

      //  doNothing().when(badgeService.deleteBadgeById(any(Principal.class), any(BadgeIdDto.class)));

        mockMvc
                .perform(delete(url + "badge").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(BadgeUtility.createBadgeIdDtoJson()))
                .andExpect(status().isOk());
    }

*/


}
