package edu.mirea.vitality.blog.mapper;

import edu.mirea.vitality.blog.domain.dto.auth.SignUpRequest;
import edu.mirea.vitality.blog.domain.dto.user.UserProfileResponse;
import edu.mirea.vitality.blog.domain.dto.user.UserResponse;
import edu.mirea.vitality.blog.domain.model.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponse toResponse(User user);

    UserProfileResponse toProfileResponse(User user);

    List<UserResponse> toResponse(List<User> users);

    User toEntity(SignUpRequest request);
}