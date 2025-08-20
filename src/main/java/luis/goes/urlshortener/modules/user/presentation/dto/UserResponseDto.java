package luis.goes.urlshortener.modules.user.presentation.dto;

import luis.goes.urlshortener.core.shared.mapper.entityToDto.DTO;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record UserResponseDto(
        UUID id,
        String name,
        String email,
        Instant createdAt,
        Instant updatedAt
) implements DTO {
}