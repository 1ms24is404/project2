package com.estateiq.service.impl;

import com.estateiq.dto.AuthResponseDTO;
import com.estateiq.dto.LoginRequestDTO;
import com.estateiq.dto.RegisterRequestDTO;
import com.estateiq.entity.User;
import com.estateiq.exception.DuplicateEmailException;
import com.estateiq.exception.InvalidCredentialsException;
import com.estateiq.repository.UserRepository;
import com.estateiq.security.JwtUtil;
import com.estateiq.service.AuthService;
import java.time.LocalDateTime;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @Override
    @Transactional
    public AuthResponseDTO register(RegisterRequestDTO requestDTO) {
        userRepository.findByEmail(requestDTO.getEmail())
                .ifPresent(existingUser -> {
                    throw new DuplicateEmailException("Email already exists");
                });

        User user = new User();
        user.setName(requestDTO.getName());
        user.setEmail(requestDTO.getEmail());
        user.setPassword(passwordEncoder.encode(requestDTO.getPassword()));
        user.setMonthlySalary(requestDTO.getMonthlySalary());
        user.setCreatedAt(LocalDateTime.now());

        userRepository.save(user);
        String token = jwtUtil.generateToken(user.getEmail());
        return new AuthResponseDTO(token, user.getEmail());
    }

    @Override
    public AuthResponseDTO login(LoginRequestDTO requestDTO) {
        User user = userRepository.findByEmail(requestDTO.getEmail())
                .orElseThrow(() -> new InvalidCredentialsException("Invalid email or password"));

        if (!passwordEncoder.matches(requestDTO.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid email or password");
        }

        return new AuthResponseDTO(jwtUtil.generateToken(user.getEmail()), user.getEmail());
    }
}
