package luis.goes.urlshortener.modules.user.application.useCase.getMethods;

import luis.goes.urlshortener.modules.user.presentation.dto.UserResponseDto;
import luis.goes.urlshortener.modules.user.presentation.dto.UserWithAuthorityDto;

import java.util.List;
import java.util.UUID;

public interface IUserGetters {

    UserResponseDto byId(UUID id);

    UserResponseDto byName(String name);

    UserResponseDto byEmail(String email);

    List<UserResponseDto> all();

    List<UserResponseDto> allDeactivated();

    UserWithAuthorityDto allUserAuthorities(UUID userId);

}