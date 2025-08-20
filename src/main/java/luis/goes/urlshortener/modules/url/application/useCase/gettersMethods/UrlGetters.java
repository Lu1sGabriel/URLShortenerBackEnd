package luis.goes.urlshortener.modules.url.application.useCase.gettersMethods;

import luis.goes.urlshortener.core.exception.HttpException;
import luis.goes.urlshortener.modules.url.domain.URLEntity;
import luis.goes.urlshortener.modules.url.infrastructure.repository.UrlRepository;
import luis.goes.urlshortener.modules.url.presentation.dto.UrlResponseDTO;
import luis.goes.urlshortener.modules.url.shared.mapper.UrlMapper;
import luis.goes.urlshortener.modules.user.domain.UserEntity;
import luis.goes.urlshortener.modules.user.infrastructure.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class UrlGetters implements IUrlGetters {
    private final UrlRepository repository;
    private final UserRepository userRepository;
    private final UrlMapper mapper;

    public UrlGetters(UrlRepository repository, UserRepository userRepository, UrlMapper mapper) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<UrlResponseDTO> getAllUserUrls(UUID userId) {
        if (userId == null) throw HttpException.badRequest("An error occurred: User ID was not provided.");

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> HttpException.notFound("We couldn't find a user with the provided ID."));

        List<URLEntity> urls = repository.findAllByUser_Id(user.getId());

        return mapper.toDtoList(urls);
    }

    @Override
    @Transactional(readOnly = true)
    public UrlResponseDTO getByShortened(String userName, String shortenedId) {
        String urlShortenedBuild = userName + "/" + shortenedId;

        URLEntity url = repository.findByShortened(urlShortenedBuild)
                .orElseThrow(() -> HttpException.notFound("We couldn't find the original URL with the provide shortened"));

        return mapper.toDto(url);
    }

}