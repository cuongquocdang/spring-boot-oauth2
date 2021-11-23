package com.example.auth.dto;

import com.example.auth.util.GeneralUtil;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class LocalUser extends User implements OAuth2User, OidcUser {

    private final OidcIdToken idToken;
    private final OidcUserInfo userInfo;
    private Map<String, Object> attributes;
    private com.example.auth.entity.auth.User userFromDatabase;

    public LocalUser(final String userID, final String password,
                     final boolean enabled, final boolean accountNonExpired, final boolean credentialsNonExpired,
                     final boolean accountNonLocked,
                     final Collection<? extends GrantedAuthority> authorities,
                     final com.example.auth.entity.auth.User userFromDatabase) {
        this(userID, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked,
                authorities, userFromDatabase, null, null);
    }

    public LocalUser(final String userID, final String password,
                     final boolean enabled, final boolean accountNonExpired, final boolean credentialsNonExpired,
                     final boolean accountNonLocked,
                     final Collection<? extends GrantedAuthority> authorities,
                     final com.example.auth.entity.auth.User userFromDatabase,
                     OidcIdToken idToken,
                     OidcUserInfo userInfo) {
        super(userID, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.userFromDatabase = userFromDatabase;
        this.idToken = idToken;
        this.userInfo = userInfo;
    }

    public static LocalUser create(com.example.auth.entity.auth.User userFromDatabase,
                                   Map<String, Object> attributes,
                                   OidcIdToken idToken, OidcUserInfo userInfo) {
        LocalUser localUser = new LocalUser(userFromDatabase.getEmail(), userFromDatabase.getPassword(),
                userFromDatabase.isEnabled(), true, true,
                true,
                GeneralUtil.buildSimpleGrantedAuthorities(userFromDatabase.getRoles()),
                userFromDatabase,
                idToken, userInfo);
        localUser.setAttributes(attributes);
        return localUser;
    }

    @Override
    public Map<String, Object> getClaims() {
        return this.attributes;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return this.attributes;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String getName() {
        return String.join(" ", List.of(this.userFromDatabase.getFirstName() + this.userFromDatabase.getLastName()));
    }

    @Override
    public OidcUserInfo getUserInfo() {
        return this.userInfo;
    }

    @Override
    public OidcIdToken getIdToken() {
        return this.idToken;
    }

    public com.example.auth.entity.auth.User getUserFromDatabase() {
        return this.userFromDatabase;
    }

}
