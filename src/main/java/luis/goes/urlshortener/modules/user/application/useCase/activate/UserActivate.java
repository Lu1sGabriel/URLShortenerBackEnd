package luis.goes.urlshortener.modules.user.application.useCase.activate;

import luis.goes.urlshortener.core.exception.HttpException;
import luis.goes.urlshortener.modules.user.domain.UserEntity;
import luis.goes.urlshortener.modules.user.infrastructure.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserActivate implements IUserActivate {
    private final UserRepository repository;

    public UserActivate(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public void activate(UUID id) {
        UserEntity user = repository.findById(id)
                .orElseThrow(() -> HttpException.notFound("We couldn't find a user with the provided ID."));

        user.getDateInfo().activate();

        repository.save(user);
    }

}