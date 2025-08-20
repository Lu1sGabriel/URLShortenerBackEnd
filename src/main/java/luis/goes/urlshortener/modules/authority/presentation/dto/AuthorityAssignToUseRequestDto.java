package luis.goes.urlshortener.modules.authority.presentation.dto;

import java.util.UUID;

public record AuthorityAssignToUseRequestDto(
        UUID authorityId,
        UUID userId
) {
}