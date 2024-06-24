package com.storeeverythin;

import com.storeeverythin.registration.RegistrationController;
import com.storeeverythin.registration.RegistrationRequest;
import com.storeeverythin.registration.RegistrationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class RegistrationControllerTest {

    @InjectMocks
    private RegistrationController registrationController;

    @Mock
    private RegistrationService registrationService;

    @Mock
    private Model model;

    @Mock
    private BindingResult bindingResult;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testShowRegistrationForm() {
        String result = registrationController.showRegistrationForm(model);

        verify(model, times(1)).addAttribute(eq("registrationRequest"), any(RegistrationRequest.class));
        assertEquals("registrationForm", result);
    }

    @Test
    public void testRegister_Success() {
        RegistrationRequest request = new RegistrationRequest();
        when(bindingResult.hasErrors()).thenReturn(false);
        when(registrationService.register(any(RegistrationRequest.class))).thenReturn("Registration successful");

        String result = registrationController.register(request, bindingResult, model);

        assertEquals("redirect:/login", result);
        verify(registrationService, times(1)).register(any(RegistrationRequest.class));
    }

    @Test
    public void testRegister_BindingErrors() {
        RegistrationRequest request = new RegistrationRequest();
        when(bindingResult.hasErrors()).thenReturn(true);

        String result = registrationController.register(request, bindingResult, model);

        assertEquals("registrationForm", result);
        verify(registrationService, times(0)).register(any(RegistrationRequest.class));
    }

    @Test
    public void testRegister_Failure() {
        RegistrationRequest request = new RegistrationRequest();
        when(bindingResult.hasErrors()).thenReturn(false);
        when(registrationService.register(any(RegistrationRequest.class))).thenReturn("Registration failed: Email already in use");

        String result = registrationController.register(request, bindingResult, model);

        assertEquals("registrationForm", result);
        verify(model, times(1)).addAttribute(eq("errorMessage"), eq("Registration failed: Email already in use"));
        verify(registrationService, times(1)).register(any(RegistrationRequest.class));
    }
}
