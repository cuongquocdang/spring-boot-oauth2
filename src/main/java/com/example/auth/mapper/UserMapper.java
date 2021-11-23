package com.example.auth.mapper;

import com.example.auth.dto.response.UserResponse;
import com.example.auth.entity.auth.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponse userEntityToUserResponse(User user);

}
