package luis.goes.urlshortener.modules.url.presentation.dto;

import java.util.UUID;

public record UrlChangeUrlNameDTO(
        UUID id,
        String urlName
) {
}