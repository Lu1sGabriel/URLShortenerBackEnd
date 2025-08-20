package luis.goes.urlshortener.modules.auth.presentation.dto;

import java.util.List;

public record AuthStatusDTO(
        boolean authenticated,
        List<String> authorities
) {}