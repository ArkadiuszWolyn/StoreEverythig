package com.storeeverythin;

import com.storeeverythin.model.UserEntity;
import com.storeeverythin.repository.UserRepository;
import com.storeeverythin.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testLoadUserByUsernameSuccess() {
        String username = "testuser";
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(username);

        when(userRepository.findUserByUsername(username)).thenReturn(Optional.of(userEntity));

        UserDetails userDetails = userService.loadUserByUsername(username);

        assertEquals(userEntity, userDetails);
        verify(userRepository, times(1)).findUserByUsername(username);
    }

    @Test
    public void testLoadUserByUsernameNotFound() {
        String username = "nonexistentuser";

        when(userRepository.findUserByUsername(username)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> {
            userService.loadUserByUsername(username);
        });

        verify(userRepository, times(1)).findUserByUsername(username);
    }
}
