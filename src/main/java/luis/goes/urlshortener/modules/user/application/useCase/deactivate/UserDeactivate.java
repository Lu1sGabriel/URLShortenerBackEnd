package luis.goes.urlshortener.modules.user.application.useCase.deactivate;

import luis.goes.urlshortener.core.exception.HttpException;
import luis.goes.urlshortener.modules.user.infrastructure.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserDeactivate implements IUserDeactivate {
    private final UserRepository repository;

    public UserDeactivate(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public void deactivate(UUID id) {
        var user = repository.findById(id)
                .orElseThrow(() -> HttpException.notFound("We couldn't find a user with the provided ID."));

        user.dateInfo.deactivate();

        repository.save(user);
    }

}