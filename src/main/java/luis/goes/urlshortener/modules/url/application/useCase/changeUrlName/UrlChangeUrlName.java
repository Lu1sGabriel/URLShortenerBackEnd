package luis.goes.urlshortener.modules.url.application.useCase.changeUrlName;

import luis.goes.urlshortener.core.exception.HttpException;
import luis.goes.urlshortener.modules.url.domain.URLEntity;
import luis.goes.urlshortener.modules.url.infrastructure.repository.UrlRepository;
import luis.goes.urlshortener.modules.url.presentation.dto.UrlChangeUrlNameDTO;
import luis.goes.urlshortener.modules.url.presentation.dto.UrlResponseDTO;
import luis.goes.urlshortener.modules.url.shared.mapper.UrlMapper;
import luis.goes.urlshortener.modules.user.domain.UserEntity;
import luis.goes.urlshortener.modules.user.infrastructure.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UrlChangeUrlName implements IUrlChangeUrlName {
    private final UrlRepository repository;
    private final UserRepository userRepository;
    private final UrlMapper mapper;

    public UrlChangeUrlName(UrlRepository repository, UserRepository userRepository, UrlMapper mapper) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    @Override
    public UrlResponseDTO change(UUID userId, UrlChangeUrlNameDTO dto) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> HttpException.notFound("We couldn't find a user with the provided ID."));

        URLEntity url = repository.findById(dto.id())
                .orElseThrow(() -> HttpException.notFound("We couldn't find a URL with the provided ID."));

        if (!url.getUser().getId().equals(user.getId())) throw HttpException.badRequest("The user ID does not match the owner of the URL.");

        url.changeUrlName(dto.urlName());

        return mapper.toDto(repository.save(url));
    }

}