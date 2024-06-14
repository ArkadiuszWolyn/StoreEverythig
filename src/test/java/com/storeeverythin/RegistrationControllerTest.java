package com.storeeverythin;

import com.storeeverythin.registration.RegistrationController;
import com.storeeverythin.registration.RegistrationRequest;
import com.storeeverythin.registration.RegistrationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
public class RegistrationControllerTest {

    @InjectMocks
    private RegistrationController registrationController;

    @Mock
    private RegistrationService registrationService;

    @Mock
    private Model model;

    @Mock
    private BindingResult bindingResult;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(registrationController).build();
    }

    @Test
    public void testShowRegistrationForm() throws Exception {
        mockMvc.perform(get("/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("registrationForm"))
                .andExpect(model().attributeExists("registrationRequest"));
    }


    @Test
    public void testRegisterSuccess() throws Exception {
        when(bindingResult.hasErrors()).thenReturn(false);
        when(registrationService.register(any(RegistrationRequest.class))).thenReturn("Registration successful");

        mockMvc.perform(post("/register")
                        .flashAttr("registrationRequest", new RegistrationRequest()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/dashboard"));

        verify(registrationService, times(1)).register(any(RegistrationRequest.class));
    }

    @Test
    public void testRegisterFailure() throws Exception {
        when(bindingResult.hasErrors()).thenReturn(false);
        when(registrationService.register(any(RegistrationRequest.class))).thenReturn("Registration failed: User already exists");

        mockMvc.perform(post("/register")
                        .flashAttr("registrationRequest", new RegistrationRequest()))
                .andExpect(status().isOk())
                .andExpect(view().name("registrationForm"))
                .andExpect(model().attributeExists("errorMessage"));

        verify(registrationService, times(1)).register(any(RegistrationRequest.class));
    }
}