package com.estateiq.service;

import com.estateiq.dto.AuthResponseDTO;
import com.estateiq.dto.LoginRequestDTO;
import com.estateiq.dto.RegisterRequestDTO;

public interface AuthService {

    AuthResponseDTO register(RegisterRequestDTO requestDTO);

    AuthResponseDTO login(LoginRequestDTO requestDTO);
}
