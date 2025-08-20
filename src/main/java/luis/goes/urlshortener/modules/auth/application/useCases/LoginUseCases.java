package luis.goes.urlshortener.modules.auth.application.useCases;

import lombok.Getter;
import luis.goes.urlshortener.modules.auth.application.useCases.login.Login;
import org.springframework.stereotype.Component;

@Component
@Getter
public class LoginUseCases {
    private final Login login;

    public LoginUseCases(Login login) {
        this.login = login;
    }

}
