package com.estateiq.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.estateiq.dto.AuthResponseDTO;
import com.estateiq.dto.RegisterRequestDTO;
import com.estateiq.entity.User;
import com.estateiq.exception.DuplicateEmailException;
import com.estateiq.repository.UserRepository;
import com.estateiq.security.JwtUtil;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

    @Mock
    private UserRepository userRepository;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Mock
    private JwtUtil jwtUtil;

    private AuthServiceImpl authService;

    @BeforeEach
    void setUp() {
        authService = new AuthServiceImpl(userRepository, passwordEncoder, jwtUtil);
    }

    @Test
    void registerShouldHashPasswordBeforeSaving() {
        RegisterRequestDTO requestDTO = new RegisterRequestDTO("Asha", "asha@example.com", "secret123", 75_000d);
        when(userRepository.findByEmail("asha@example.com")).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(jwtUtil.generateToken("asha@example.com")).thenReturn("token-123");

        AuthResponseDTO responseDTO = authService.register(requestDTO);

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());
        User savedUser = userCaptor.getValue();

        assertNotEquals("secret123", savedUser.getPassword());
        assertEquals("asha@example.com", savedUser.getEmail());
        assertEquals("token-123", responseDTO.getToken());
        assertEquals("asha@example.com", responseDTO.getEmail());
        org.junit.jupiter.api.Assertions.assertTrue(passwordEncoder.matches("secret123", savedUser.getPassword()));
    }

    @Test
    void registerShouldRejectDuplicateEmail() {
        RegisterRequestDTO requestDTO = new RegisterRequestDTO("Asha", "asha@example.com", "secret123", 75_000d);
        when(userRepository.findByEmail("asha@example.com")).thenReturn(Optional.of(new User()));

        assertThrows(DuplicateEmailException.class, () -> authService.register(requestDTO));
        verify(userRepository, never()).save(any(User.class));
    }
}