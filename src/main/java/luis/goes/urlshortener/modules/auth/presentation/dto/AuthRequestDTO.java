package luis.goes.urlshortener.modules.auth.presentation.dto;

public record AuthRequestDTO(
        String email,
        String password
) {
}
