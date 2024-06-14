package com.storeeverythin;

import com.storeeverythin.model.Role;
import com.storeeverythin.model.UserEntity;
import com.storeeverythin.registration.RegistrationRequest;
import com.storeeverythin.registration.RegistrationService;
import com.storeeverythin.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class RegistrationServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @InjectMocks
    private RegistrationService registrationService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testRegisterSuccess() {
        RegistrationRequest request = new RegistrationRequest();
        request.setFirstName("John");
        request.setLastName("Doe");
        request.setUsername("johndoe");
        request.setPassword("password123");
        request.setAge(30);
        request.setRoles(List.of(Role.FULL_USER));

        when(userRepository.findUserByUsername("johndoe")).thenReturn(Optional.empty());
        when(bCryptPasswordEncoder.encode("password123")).thenReturn("encodedPassword");

        String result = registrationService.register(request);

        assertEquals("Registration successful for user: johndoe", result);
        verify(userRepository, times(1)).findUserByUsername("johndoe");
        verify(bCryptPasswordEncoder, times(1)).encode("password123");
        verify(userRepository, times(1)).save(any(UserEntity.class));
    }

    @Test
    public void testRegisterUserAlreadyExists() {
        RegistrationRequest request = new RegistrationRequest();
        request.setUsername("johndoe");

        UserEntity existingUser = new UserEntity();
        when(userRepository.findUserByUsername("johndoe")).thenReturn(Optional.of(existingUser));

        String result = registrationService.register(request);

        assertEquals("Username is already taken", result);
        verify(userRepository, times(1)).findUserByUsername("johndoe");
        verify(bCryptPasswordEncoder, never()).encode(anyString());
        verify(userRepository, never()).save(any(UserEntity.class));
    }
}
