package luis.goes.urlshortener.modules.user.presentation.dto;

public record UserChangePasswordDTO(
        String currentPassword,
        String password,
        String confirmPassword
) {
}