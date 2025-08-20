package luis.goes.urlshortener.modules.url.application.useCase.changeUrl;

import luis.goes.urlshortener.core.exception.HttpException;
import luis.goes.urlshortener.modules.url.infrastructure.repository.UrlRepository;
import luis.goes.urlshortener.modules.url.presentation.dto.UrlChangeUrlDTO;
import luis.goes.urlshortener.modules.url.presentation.dto.UrlResponseDTO;
import luis.goes.urlshortener.modules.url.shared.mapper.UrlMapper;
import luis.goes.urlshortener.modules.user.infrastructure.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UrlChangeUrl implements IUrlChangeUrl {
    private final UrlRepository repository;
    private final UserRepository userRepository;
    private final UrlMapper mapper;

    public UrlChangeUrl(UrlRepository repository, UserRepository userRepository, UrlMapper mapper) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    @Override
    public UrlResponseDTO change(UUID userId, UrlChangeUrlDTO dto) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> HttpException.notFound("We couldn't find a user with the provided ID."));

        var url = repository.findById(dto.id())
                .orElseThrow(() -> HttpException.notFound("We couldn't find a URL with the provided ID."));

        if (!url.getUser().getId().equals(user.getId())) throw HttpException.badRequest("The user ID does not match the owner of the URL.");

        url.changeOriginalUrl(dto.url());

        return mapper.toDto(repository.save(url));
    }

}