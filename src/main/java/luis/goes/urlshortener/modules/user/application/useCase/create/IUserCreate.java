package luis.goes.urlshortener.modules.user.application.useCase.create;

import luis.goes.urlshortener.modules.user.presentation.dto.UserRequestDTO;
import luis.goes.urlshortener.modules.user.presentation.dto.UserResponseDto;

public interface IUserCreate {

    UserResponseDto create(UserRequestDTO dto);

}