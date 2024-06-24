package com.storeeverythin;

import com.storeeverythin.model.UserEntity;
import com.storeeverythin.registration.RegistrationRequest;
import com.storeeverythin.registration.RegistrationService;
import com.storeeverythin.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class RegistrationServiceTest {

    @InjectMocks
    private RegistrationService registrationService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegister_Success() {
        RegistrationRequest request = new RegistrationRequest();
        request.setUsername("testUser");
        request.setFirstName("John");
        request.setLastName("Doe");
        request.setPassword("password");
        request.setAge(30);

        when(userRepository.findUserByUsername(anyString())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");

        String result = registrationService.register(request);

        assertEquals("Registration successful for user: testUser", result);
        verify(userRepository, times(1)).save(any(UserEntity.class));
    }

    @Test
    public void testRegister_UsernameTaken() {
        RegistrationRequest request = new RegistrationRequest();
        request.setUsername("testUser");

        UserEntity existingUser = new UserEntity();
        when(userRepository.findUserByUsername(anyString())).thenReturn(Optional.of(existingUser));

        String result = registrationService.register(request);

        assertEquals("Username is already taken", result);
        verify(userRepository, times(0)).save(any(UserEntity.class));
    }
}

