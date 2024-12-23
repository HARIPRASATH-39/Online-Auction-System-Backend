package com.cts.auction;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.cts.auction.Entity.UserEntity;
import com.cts.auction.Exception.UserNotFoundException;
import com.cts.auction.Repository.UserRepository;
import com.cts.auction.Service.UserServiceImpl;
import com.cts.auction.Validation.UserDTO;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    private UserDTO userDTO;
    private UserEntity userEntity;

    @BeforeEach
    void setUp() {
        userDTO = UserDTO.builder()
                .username("testuser")
                .email("testuser@example.com")
                .password("password")
                .wallet_amount(100.0)
                .build();

        userEntity = UserEntity.builder()
                .username("testuser")
                .email("testuser@example.com")
                .password("encodedPassword")
                .wallet_amount(100.0)
                .build();
    }

    @Test
    void testSignup_UserAlreadyRegistered() {
        when(userRepository.findByEmail(userDTO.getEmail())).thenReturn(userEntity);

        String result = userService.signup(userDTO);

        assertEquals("The email address is already registered.", result);
    }

    @Test
    void testSignup_NewUser() {
        when(userRepository.findByEmail(userDTO.getEmail())).thenReturn(null);
        when(passwordEncoder.encode(userDTO.getPassword())).thenReturn("encodedPassword");

        String result = userService.signup(userDTO);

        assertEquals("Account created successfuly", result);
        verify(userRepository, times(1)).save(any(UserEntity.class));
    }

    @Test
    void testLogin_Success() {
        when(userRepository.findByUsername(userEntity.getUsername())).thenReturn(Optional.of(userEntity));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);

        String result = userService.login(userEntity);

        assertEquals("Login Successful", result);
    }

    @Test
    void testLogin_Failure() {
        when(userRepository.findByUsername(userEntity.getUsername())).thenReturn(Optional.of(userEntity));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            userService.login(userEntity);
        });

        assertEquals("Invalid Credentials", exception.getMessage());
    }

    @Test
    void testFindAllUsers() {
        userService.findAllUsers();
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void testFindUserById_Success() {
        when(userRepository.findById(1)).thenReturn(Optional.of(userEntity));

        UserEntity result = userService.findUserById(1);

        assertNotNull(result);
        assertEquals(userEntity, result);
    }

    @Test
    void testFindUserById_Failure() {
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        Exception exception = assertThrows(UserNotFoundException.class, () -> {
            userService.findUserById(1);
        });

        assertEquals("No user by ID: 1", exception.getMessage());
    }

    @Test
    void testAddAmount_Success() {
        when(userRepository.findById(1)).thenReturn(Optional.of(userEntity));

        String result = userService.addAmount(1, 50.0);

        assertEquals("Amount added successfull", result);
        verify(userRepository, times(1)).save(userEntity);
    }

    @Test
    void testDeleteUser_Success() {
        when(userRepository.findById(1)).thenReturn(Optional.of(userEntity));

        String result = userService.deleteUser(1);

        assertEquals("User Removed", result);
        verify(userRepository, times(1)).delete(userEntity);
    }
}
