package com.example.auth.service.impl;

import com.example.auth.dto.LocalUser;
import com.example.auth.dto.UserInfo;
import com.example.auth.dto.request.RegisterRequest;
import com.example.auth.entity.auth.ManagementType;
import com.example.auth.entity.auth.Role;
import com.example.auth.entity.auth.User;
import com.example.auth.exception.AppException;
import com.example.auth.exception.AuthException;
import com.example.auth.exception.OAuth2AuthenticationProcessingException;
import com.example.auth.repository.RoleRepository;
import com.example.auth.repository.UserRepository;
import com.example.auth.security.oauth2.user.OAuth2UserInfo;
import com.example.auth.security.oauth2.user.OAuth2UserInfoFactory;
import com.example.auth.service.UserService;
import com.example.auth.util.GeneralUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Service("userService")
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public UserInfo registerNewUser(RegisterRequest request) {
       if (userRepository.existsByEmail(request.getEmail())) {
            throw new AuthException(HttpStatus.UNPROCESSABLE_ENTITY, String.format("User with email %s already exist", request.getEmail()));
       }
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());

        Role foundRole = roleRepository.findByName(ManagementType.ADMIN.name())
                .orElseThrow(() -> {
                    log.error("Not found role by name {}.", ManagementType.ADMIN.name());
                    return new AppException(HttpStatus.NOT_FOUND, String.format("Not found role by name {%s}.", ManagementType.ADMIN.name()));
                });
        user.setRoles(Set.of(foundRole));

        user.setEnabled(true);
        userRepository.saveAndFlush(user);

        return new UserInfo(user.getEmail(), user.getFirstName(),
                user.getLastName(), GeneralUtil.toRoles(user.getRoles()));
    }

    @Override
    public LocalUser processUserRegistration(String registrationId, Map<String, Object> attributes,
                                             OidcIdToken idToken, OidcUserInfo userInfo) {
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(registrationId, attributes);
        if (StringUtils.hasLength(oAuth2UserInfo.getName())) {
            log.error("Name not found from OAuth2 provider");
            throw new OAuth2AuthenticationProcessingException("Name not found from OAuth2 provider");
        } else if (StringUtils.hasLength(oAuth2UserInfo.getEmail())) {
            log.error("Email not found from OAuth2 provider");
            throw new OAuth2AuthenticationProcessingException("Email not found from OAuth2 provider");
        }

        RegisterRequest request = toUserRegistration(registrationId, oAuth2UserInfo);

        Optional<User> user = userRepository.findByEmail(oAuth2UserInfo.getEmail());

        User userFromDatabase = new User();

        if (user.isEmpty()) {
            registerNewUser(request);
            userFromDatabase.setEmail(request.getEmail());
        } else {
            userFromDatabase = user.get();
        }

        return LocalUser.create(userFromDatabase, attributes, idToken, userInfo);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    @Override
    public User findUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    private RegisterRequest toUserRegistration(String registrationId, OAuth2UserInfo oAuth2UserInfo) {
        return RegisterRequest.builder().providerUserId(oAuth2UserInfo.getId())
                .firstName(oAuth2UserInfo.getName())
                .email(oAuth2UserInfo.getEmail())
                .socialProvider(GeneralUtil.toSocialProvider(registrationId))
                .password("changeit")
                .build();
    }

}
