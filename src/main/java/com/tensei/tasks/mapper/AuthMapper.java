package com.tensei.tasks.mapper;

import com.tensei.tasks.domain.dto.auth.UserLoginResponse;
import com.tensei.tasks.domain.dto.auth.UserRegisterRequest;
import com.tensei.tasks.domain.dto.auth.UserRegisterResponse;
import com.tensei.tasks.domain.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthMapper {

    UserLoginResponse toAuthResponseDto(User user);
    User fromAuthResponseDto(UserLoginResponse authResponse);

    UserRegisterResponse toUserRegisterResponse(User user);
    User fromUserRegisterRequest(UserRegisterRequest request);

}
