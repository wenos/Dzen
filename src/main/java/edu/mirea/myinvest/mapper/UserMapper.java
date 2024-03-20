package edu.mirea.myinvest.mapper;

import edu.mirea.myinvest.domain.dto.auth.SignUpRequest;
import edu.mirea.myinvest.domain.dto.user.UserProfileResponse;
import edu.mirea.myinvest.domain.dto.user.UserResponse;
import edu.mirea.myinvest.domain.model.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponse toResponse(User user);

    UserProfileResponse toProfileResponse(User user);

    List<UserResponse> toResponse(List<User> users);

    User toEntity(SignUpRequest request);



}