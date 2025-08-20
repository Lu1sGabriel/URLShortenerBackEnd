package luis.goes.urlshortener.modules.url.application.useCase.create;

import luis.goes.urlshortener.core.exception.HttpException;
import luis.goes.urlshortener.core.shared.utils.NameFormatter;
import luis.goes.urlshortener.modules.url.domain.URLEntity;
import luis.goes.urlshortener.modules.url.infrastructure.repository.UrlRepository;
import luis.goes.urlshortener.modules.url.presentation.dto.UrlRequestDTO;
import luis.goes.urlshortener.modules.url.presentation.dto.UrlResponseDTO;
import luis.goes.urlshortener.modules.url.shared.mapper.UrlMapper;
import luis.goes.urlshortener.modules.user.infrastructure.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UrlCreate implements IUrlCreate {

    private final UrlRepository repository;
    private final UserRepository userRepository;
    private final UrlMapper mapper;

    public UrlCreate(UrlRepository repository, UserRepository userRepository, UrlMapper mapper) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    @Override
    public UrlResponseDTO create(UUID userId, UrlRequestDTO dto) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> HttpException.notFound("We couldn't find a user with the provided ID."));

        UUID id;
        do {
            id = UUID.randomUUID();
        } while (idPrefixExists(id));

        String userName = getFirstAndLastUserName(user.getName().getValue().split(" "));
        String idPrefix = extractIdPrefix(id);
        String shortenedUrl = buildShortenedUrl(userName, idPrefix);

        URLEntity url = new URLEntity(dto.urlName(), dto.url(), shortenedUrl, user);
        return mapper.toDto(repository.save(url));
    }

    private boolean idPrefixExists(UUID uuid) {
        String prefix = extractIdPrefix(uuid);

        return repository.findAll().stream()
                .map(url -> {
                    String shortened = url.getShortened();
                    int idx = shortened.lastIndexOf("/");
                    return (idx != -1) ? shortened.substring(idx + 1) : shortened;
                })
                .anyMatch(idPart -> idPart.startsWith(prefix));
    }

    private String extractIdPrefix(UUID uuid) {
        return uuid.toString().split("-")[0];
    }

    private String getFirstAndLastUserName(String[] splittedName) {
        int length = splittedName.length;
        String firstName = NameFormatter.removeAccents(splittedName[0]).toLowerCase();
        String lastName = NameFormatter.removeAccents(splittedName[length - 1]).toLowerCase();

        return NameFormatter.removeAccents(firstName).toLowerCase() + "-" + NameFormatter.removeAccents(lastName).toLowerCase();
    }

    private String buildShortenedUrl(String userName, String id) {
        if (id == null || id.isBlank()) throw new IllegalArgumentException("ID cannot be null or empty.");
        return String.format("%s/%s", userName, id);
    }

}
