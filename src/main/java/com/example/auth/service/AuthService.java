package com.example.auth.service;

import com.example.auth.dto.request.LoginRequest;
import com.example.auth.dto.response.JwtResponse;

public interface AuthService {

    JwtResponse loginUser(LoginRequest request);

}
