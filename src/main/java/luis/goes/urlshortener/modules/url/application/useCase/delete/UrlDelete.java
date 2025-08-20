package luis.goes.urlshortener.modules.url.application.useCase.delete;

import luis.goes.urlshortener.core.exception.HttpException;
import luis.goes.urlshortener.modules.url.domain.URLEntity;
import luis.goes.urlshortener.modules.url.infrastructure.repository.UrlRepository;
import luis.goes.urlshortener.modules.user.domain.UserEntity;
import luis.goes.urlshortener.modules.user.infrastructure.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UrlDelete implements IUrlDelete {
    private final UrlRepository repository;
    private final UserRepository userRepository;

    public UrlDelete(UrlRepository repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    @Override
    public void delete(UUID userId, UUID id) {
        if (id == null) throw HttpException.badRequest("An error occurred: ID was not provided.");

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> HttpException.notFound("We couldn't find a user with the provided ID."));

        URLEntity url = repository.findByUser_IdAndId(user.getId(), id)
                .orElseThrow(() -> HttpException.notFound("We couldn't find a URL with the provided ID."));

        repository.delete(url);
    }

}