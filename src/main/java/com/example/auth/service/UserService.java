package com.example.auth.service;

import com.example.auth.dto.LocalUser;
import com.example.auth.dto.UserInfo;
import com.example.auth.dto.request.RegisterRequest;
import com.example.auth.entity.auth.User;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;

import java.util.List;
import java.util.Map;

public interface UserService {

    List<User> getAllUsers();

    UserInfo registerNewUser(RegisterRequest request);

    LocalUser processUserRegistration(String registrationId, Map<String, Object> attributes,
                                      OidcIdToken idToken, OidcUserInfo userInfo);

    User findUserByEmail(String email);

    User findUserById(Long id);

}
