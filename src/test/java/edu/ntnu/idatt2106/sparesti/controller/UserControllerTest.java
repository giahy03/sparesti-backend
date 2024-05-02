package edu.ntnu.idatt2106.sparesti.controller;


import edu.ntnu.idatt2106.sparesti.model.user.util.UserUtility;
import edu.ntnu.idatt2106.sparesti.repository.user.UserRepository;
import edu.ntnu.idatt2106.sparesti.service.auth.JwtService;
import edu.ntnu.idatt2106.sparesti.service.user.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@ExtendWith(MockitoExtension.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @InjectMocks
    UserController userController;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtService jwtService;
    @MockBean
    private UserService userService;
    @MockBean
    private UserRepository userRepository;

    String url = "/api/v1/users";


    @DisplayName("Test that controller is initialized.")
    @Test
    public void controllerIsInitialized() {
        assertThat(userController).isNotNull();
    }


    @DisplayName("Test change password end-point")
    @Test
    @WithMockUser(password = "abc", username = "abc@email.com", roles = "USER")
    public void controller_changePasswordEndPoint() throws Exception {

        mockMvc
                .perform(put(url + "/password").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(UserUtility.createPasswordChangeDtoJson()))
                .andExpect(status().isOk());
    }

    @DisplayName("Test change user first name")
    @Test
    @WithMockUser(password = "abc", username = "abc@email.com", roles = "USER")
    public void controller_changeFirstName() throws Exception {

        mockMvc
                .perform(put(url + "/first-name").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(UserUtility.createFirstNameChangeDtoJson()))
                .andExpect(status().isOk());
    }

    @DisplayName("Test change user last name")
    @Test
    @WithMockUser(password = "abc", username = "abc@email.com", roles = "USER")
    public void controller_changeLastName() throws Exception {

        mockMvc
                .perform(put(url + "/last-name").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(UserUtility.createLastNameChangeDtoJson()))
                .andExpect(status().isOk());
    }


    @DisplayName("Test change user income")
    @Test
    @WithMockUser(password = "abc", username = "abc@email.com", roles = "USER")
    public void controller_changeUserIncome() throws Exception {

        mockMvc
                .perform(put(url + "/income").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(UserUtility.createIncomeChangeDtoJson()))
                .andExpect(status().isOk());
    }

    @DisplayName("Test change user's living status")
    @Test
    @WithMockUser(password = "abc", username = "abc@email.com", roles = "USER")
    public void controller_changeUserLivingStatus() throws Exception {

        mockMvc
                .perform(put(url + "/living-status").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(UserUtility.createLivingStatusChangeDtoJson()))
                .andExpect(status().isOk());
    }



}
