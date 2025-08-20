package luis.goes.urlshortener.modules.url.presentation.dto;

import luis.goes.urlshortener.core.shared.mapper.entityToDto.DTO;

public record UrlRequestDTO(
        String urlName,
        String url
) implements DTO {
}