package edu.ntnu.idatt2106.sparesti.controller;

import edu.ntnu.idatt2106.sparesti.dto.saving.SavingGoalContributionDto;
import edu.ntnu.idatt2106.sparesti.dto.saving.SavingGoalCreationRequestDto;
import edu.ntnu.idatt2106.sparesti.dto.saving.SavingGoalIdDto;
import edu.ntnu.idatt2106.sparesti.mapper.SavingGoalMapper;
import edu.ntnu.idatt2106.sparesti.model.saving.util.SavingGoalUtility;
import edu.ntnu.idatt2106.sparesti.repository.SavingGoalRepository;
import edu.ntnu.idatt2106.sparesti.repository.user.UserRepository;
import edu.ntnu.idatt2106.sparesti.service.auth.JwtService;
import edu.ntnu.idatt2106.sparesti.service.saving.SavingGoalService;
import edu.ntnu.idatt2106.sparesti.service.user.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * Unit tests for the controller class of saving goal.
 * The class tests POST, GET, PUT and DELETE endpoints of the controller.
 *
 * @author Hanne-Sofie Søreide
 */

//@SpringBootTest
@ExtendWith(MockitoExtension.class)
@WebMvcTest(SavingGoalController.class)
public class SavingGoalControllerTest {

    @InjectMocks
    SavingGoalController savingGoalController;

    @Autowired private MockMvc mockMvc;

    @Mock
    private Pageable pageable;

    @MockBean
    private SavingGoalService savingGoalService;
    @MockBean
    private SavingGoalRepository savingGoalRepository;
    @MockBean
    private SavingGoalMapper savingGoalMapper;

    @MockBean
    private JwtService jwtService;
    @MockBean
    private UserService userService;
    @MockBean
    private UserRepository userRepository;

    String url = "/api/v1/goal-manager/";

    @DisplayName("Test that controller is initialized")
    @Test
    public void controllerInitializedCorrectly() {
        assertThat(savingGoalController).isNotNull();
    }


    @DisplayName("Test that the controller responds correctly to a POST request to add a saving goal.")
    @Test
    @WithMockUser(roles = "USER")
    public void controllerAddsSavingGoal() throws Exception {

        when(savingGoalService.createSavingGoal(any(Principal.class), any(SavingGoalCreationRequestDto.class))).thenReturn(SavingGoalUtility.createSavingGoalIdDto());

        mockMvc
                .perform(post(url + "goals").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(SavingGoalUtility.createSavingGoalCreationRequestDtoJson()))
                .andExpect(status().isCreated())
                .andExpect(content().json(SavingGoalUtility.createSavingGoalIdDtoJson()));
    }


    @DisplayName("Test that the controller responds correctly to GET request retrieving a saving goal by its id.")
    @Test
    @WithMockUser(roles = "USER")
    public void controllerGetSavingGoalCorrectly() throws Exception {

        when(savingGoalService.getSavingGoalById(any(Principal.class), any(Long.class))).thenReturn(SavingGoalUtility.createSavingGoalDto());

        mockMvc
                .perform(get(url + "goal/1").with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().json(SavingGoalUtility.createSavingGoalDtoJson()));
    }


    @DisplayName("Test that the controller responds correctly to PUT request to add a new saved amount.")
    @Test
    @WithMockUser(roles = "USER")
    public void controllerPutsSavingContributionCorrectly() throws Exception {

        when(savingGoalService.registerSavingContribution(any(Principal.class), any(SavingGoalContributionDto.class))).thenReturn(750.0);

        mockMvc
                .perform(put(url + "goal/save").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(SavingGoalUtility.createSavingGoalContributionDtoJson()))
                .andExpect(status().isOk());
    }

    @DisplayName("Test that the controller responds correctly to GET request retrieving a list og goals by user email.")
    @Test
    @WithMockUser(roles = "USER")
    public void controllerGetSavingGoalsByEmailCorrectly() throws Exception {
        SavingGoalIdDto idDto1 = SavingGoalUtility.createSavingGoalIdDto();
        SavingGoalIdDto idDto2 = SavingGoalUtility.createSavingGoalIdDto1();
        SavingGoalIdDto idDto3 = SavingGoalUtility.createSavingGoalIdDto2();
        List<SavingGoalIdDto> goals = Arrays.asList(idDto1, idDto2, idDto3);

        pageable = PageRequest.of(0, 10);
        when(savingGoalService.getAllGoalsOfUser(any(Principal.class),any(PageRequest.class))).thenReturn(goals);

        mockMvc
                .perform(get(url + "goals").with(csrf())
                        .param("page", "0")
                        .param("pageSize", "10"))
                .andExpect(status().isOk())
                .andExpect(content().json(SavingGoalUtility.createSavingGoalIdDtoListJson()));
    }


    @DisplayName("Test that the controller responds correctly to DELETE request.")
    @Test
    @WithMockUser
    public void controllerDeleteSavingGoalCorrectly() throws Exception {
        mockMvc
                .perform(delete(url + "goal").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(SavingGoalUtility.createSavingGoalIdDtoJson()))
                .andExpect(status().isOk());
    }

}