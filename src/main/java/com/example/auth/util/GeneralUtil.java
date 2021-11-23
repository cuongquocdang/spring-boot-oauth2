package com.example.auth.util;

import com.example.auth.dto.LocalUser;
import com.example.auth.dto.SocialProvider;
import com.example.auth.dto.UserInfo;
import com.example.auth.entity.auth.Role;
import com.example.auth.entity.auth.User;
import lombok.experimental.UtilityClass;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@UtilityClass
public class GeneralUtil {

    public static List<SimpleGrantedAuthority> buildSimpleGrantedAuthorities(final Set<Role> roles) {
        return roles.stream()
                .map(r -> new SimpleGrantedAuthority(r.getName().name()))
                .collect(Collectors.toList());
    }

    public static UserInfo buildUserInfo(LocalUser localUser) {
        List<String> roles = localUser.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        User userFromDatabase = localUser.getUserFromDatabase();
        return new UserInfo(userFromDatabase.getEmail(),
                userFromDatabase.getFirstName(), userFromDatabase.getLastName(),
                roles);
    }

    public static List<String> toRoles(Set<Role> roles) {
        return roles.stream()
                .map(r -> r.getName().name())
                .collect(Collectors.toList());
    }

    public static SocialProvider toSocialProvider(String providerId) {
        for (SocialProvider socialProvider : SocialProvider.values()) {
            if (socialProvider.getProviderType().equals(providerId)) {
                return socialProvider;
            }
        }
        return SocialProvider.LOCAL;
    }

}
