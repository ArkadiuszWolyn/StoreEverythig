package com.storeeverythin;

import com.storeeverythin.model.UserEntity;
import com.storeeverythin.repository.UserRepository;
import com.storeeverythin.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testLoadUserByUsername_UserFound() {
        UserEntity user = new UserEntity();
        when(userRepository.findUserByUsername(anyString())).thenReturn(Optional.of(user));

        UserDetails result = userService.loadUserByUsername("testUser");

        assertEquals(user, result);
    }

    @Test
    public void testLoadUserByUsername_UserNotFound() {
        when(userRepository.findUserByUsername(anyString())).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> {
            userService.loadUserByUsername("testUser");
        });
    }

    @Test
    public void testGetAllUsers() {
        List<UserEntity> users = new ArrayList<>();
        when(userRepository.findAll()).thenReturn(users);

        List<UserEntity> result = userService.getAllUsers();

        assertEquals(users, result);
    }

    @Test
    public void testGetUserById_UserFound() {
        UserEntity user = new UserEntity();
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        UserEntity result = userService.getUserById(1L);

        assertEquals(user, result);
    }

    @Test
    public void testGetUserById_UserNotFound() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            userService.getUserById(1L);
        });
    }

    @Test
    public void testSaveUser() {
        UserEntity user = new UserEntity();
        userService.saveUser(user);

        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testDeleteUserById() {
        doNothing().when(userRepository).deleteById(anyLong());

        userService.deleteUserById(1L);

        verify(userRepository, times(1)).deleteById(anyLong());
    }

    @Test
    public void testEncodePassword() {
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");

        String result = userService.encodePassword("password");

        assertEquals("encodedPassword", result);
    }
}
