package edu.mirea.myinvest.mapper;

import edu.mirea.myinvest.domain.dto.auth.SignUpRequest;
import edu.mirea.myinvest.domain.dto.user.UserProfileResponse;
import edu.mirea.myinvest.domain.dto.user.UserResponse;
import edu.mirea.myinvest.domain.model.User;
import edu.mirea.myinvest.domain.model.UserSecurityRel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "subscribes", source = "securities", qualifiedByName = "setSubscribes")
    UserResponse toResponse(User user);

    UserProfileResponse toProfileResponse(User user);

    List<UserResponse> toResponse(List<User> users);

    User toEntity(SignUpRequest request);

    @Named("setSubscribes")
    default Set<Long> setSubscribes(List<UserSecurityRel> securities) {
        Set<Long> subscribes = new HashSet<>();
        for (var item : securities) {
            subscribes.add(item.getSecurity().getId());
        }
        return subscribes;
    }

}