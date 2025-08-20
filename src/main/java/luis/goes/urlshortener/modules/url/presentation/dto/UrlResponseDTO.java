package luis.goes.urlshortener.modules.url.presentation.dto;

import luis.goes.urlshortener.core.shared.mapper.entityToDto.DTO;

import java.util.UUID;

public record UrlResponseDTO(
        UUID id,
        String urlName,
        String url,
        String shortened,
        UserInfo user
) implements DTO {
    public record UserInfo(
            UUID id,
            String name,
            String email
    ) {
    }

}