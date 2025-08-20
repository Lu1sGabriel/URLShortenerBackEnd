package luis.goes.urlshortener.modules.authority.presentation.dto;

import java.util.UUID;

public record AuthorityWithUserDto(
        UUID id,
        String name,
        String description,
        UUID userId,
        String userName
) {
}