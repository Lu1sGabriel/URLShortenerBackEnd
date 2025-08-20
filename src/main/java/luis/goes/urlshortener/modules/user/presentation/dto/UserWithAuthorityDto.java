package luis.goes.urlshortener.modules.user.presentation.dto;

import luis.goes.urlshortener.modules.authority.presentation.dto.AuthorityResponseDTO;

import java.util.List;
import java.util.UUID;

public record UserWithAuthorityDto(
        UUID id,
        String name,
        List<AuthorityResponseDTO> authorities
) {
}