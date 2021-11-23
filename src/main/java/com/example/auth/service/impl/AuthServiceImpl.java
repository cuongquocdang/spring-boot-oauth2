package com.example.auth.service.impl;

import com.example.auth.dto.LocalUser;
import com.example.auth.dto.request.LoginRequest;
import com.example.auth.dto.response.JwtResponse;
import com.example.auth.security.jwt.JwtProvider;
import com.example.auth.service.AuthService;
import com.example.auth.util.GeneralUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service("jwtAuthService")
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtManager;

    @Autowired
    public AuthServiceImpl(AuthenticationManager authenticationManager, JwtProvider jwtManager) {
        this.authenticationManager = authenticationManager;
        this.jwtManager = jwtManager;
    }

    @Override
    public JwtResponse loginUser(LoginRequest request) {
        final String email = request.getEmail();
        final String password = request.getPassword();
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtManager.createToken(authentication);
        LocalUser localUser = (LocalUser) authentication.getPrincipal();
        return new JwtResponse(jwt, GeneralUtil.buildUserInfo(localUser));
    }

}
