package luis.goes.urlshortener.modules.user.application.useCase.changeName;

import luis.goes.urlshortener.modules.user.presentation.dto.UserChangeNameDTO;
import luis.goes.urlshortener.modules.user.presentation.dto.UserResponseDto;

import java.util.UUID;

public interface IUserChangeName {

    UserResponseDto change(UUID id, UserChangeNameDTO dto);
}
