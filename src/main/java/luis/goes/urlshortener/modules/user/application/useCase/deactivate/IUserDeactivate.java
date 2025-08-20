package luis.goes.urlshortener.modules.user.application.useCase.deactivate;

import java.util.UUID;

public interface IUserDeactivate {
    void deactivate(UUID id);
}