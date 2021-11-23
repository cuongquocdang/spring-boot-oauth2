package com.example.auth.service.impl;

import com.example.auth.dto.LocalUser;
import com.example.auth.entity.auth.User;
import com.example.auth.exception.AppException;
import com.example.auth.service.UserService;
import com.example.auth.util.GeneralUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("localUserDetailService")
@Slf4j
public class LocalUserDetailService implements UserDetailsService {

    private final UserService userService;

    @Autowired
    public LocalUserDetailService(@Qualifier("userService") @Lazy UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
        User userFound = userService.findUserByEmail(email);

        if (null != userFound) {
            log.info("Found user by email {}.", email);
            return createLocalUser(userFound);
        }

        log.error("Not found user by email {}.", email);
        throw new AppException(HttpStatus.NOT_FOUND, String.format("Not found user by email {%s}.", email));
    }

    public LocalUser loadUserById(final Long id) {
        User userFound = userService.findUserById(id);

        if (null != userFound) {
            log.info("Found user by id {}.", id);
            return createLocalUser(userFound);
        }

        log.error("Not found user by id {}.", id);
        throw new AppException(HttpStatus.NOT_FOUND, String.format("Not found user by id {%s}.", id));

    }

    private LocalUser createLocalUser(User user) {
        return new LocalUser(user.getEmail(),
                user.getPassword(),
                user.isEnabled(),
                true,
                true,
                true,
                GeneralUtil.buildSimpleGrantedAuthorities(user.getRoles()), user);
    }

}
