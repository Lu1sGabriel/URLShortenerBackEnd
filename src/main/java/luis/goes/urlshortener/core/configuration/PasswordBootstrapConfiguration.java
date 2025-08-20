package luis.goes.urlshortener.core.configuration;

import jakarta.annotation.PostConstruct;
import luis.goes.urlshortener.modules.user.valueObject.Password;
import luis.goes.urlshortener.core.shared.helpers.password.PasswordHash;
import org.springframework.stereotype.Component;

@Component
public class PasswordBootstrapConfiguration {

    private final PasswordHash passwordHash;

    public PasswordBootstrapConfiguration(PasswordHash passwordHash) {
        this.passwordHash = passwordHash;
    }

    @PostConstruct
    public void init() {
        Password.injectHasher(passwordHash);
    }

}