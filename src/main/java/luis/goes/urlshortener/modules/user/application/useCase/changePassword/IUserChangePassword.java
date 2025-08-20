package luis.goes.urlshortener.modules.user.application.useCase.changePassword;

import luis.goes.urlshortener.modules.user.presentation.dto.UserChangePasswordDTO;
import luis.goes.urlshortener.modules.user.presentation.dto.UserResponseDto;

import java.util.UUID;

public interface IUserChangePassword {

    UserResponseDto change(UUID id, UserChangePasswordDTO dto);

}