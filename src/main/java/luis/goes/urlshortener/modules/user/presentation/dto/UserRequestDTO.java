package luis.goes.urlshortener.modules.user.presentation.dto;

import luis.goes.urlshortener.core.shared.mapper.entityToDto.DTO;

public record UserRequestDTO(
        String name,
        String email,
        String password
) implements DTO {
}