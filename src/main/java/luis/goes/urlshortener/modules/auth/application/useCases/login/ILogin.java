package luis.goes.urlshortener.modules.auth.application.useCases.login;

import luis.goes.urlshortener.modules.auth.presentation.dto.AuthRequestDTO;

public interface ILogin {
    String login(AuthRequestDTO dto);
}
