package luis.goes.urlshortener.modules.authority.presentation.dto;

import luis.goes.urlshortener.core.shared.mapper.entityToDto.DTO;

import java.util.UUID;

public record AuthorityResponseDTO(
        UUID id,
        String authority,
        String description
) implements DTO {
}