package luis.goes.urlshortener.modules.user.application.useCase.changeEmail;

import luis.goes.urlshortener.modules.user.presentation.dto.UserChangeEmailDTO;
import luis.goes.urlshortener.modules.user.presentation.dto.UserResponseDto;

import java.util.UUID;

public interface IUserChangeEmail {

    UserResponseDto change(UUID id, UserChangeEmailDTO dto);

}