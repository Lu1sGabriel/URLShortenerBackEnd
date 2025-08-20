package luis.goes.urlshortener.modules.user.application.useCase.changePassword;

import luis.goes.urlshortener.core.exception.HttpException;
import luis.goes.urlshortener.modules.user.domain.UserEntity;
import luis.goes.urlshortener.modules.user.infrastructure.repository.UserRepository;
import luis.goes.urlshortener.modules.user.presentation.dto.UserChangePasswordDTO;
import luis.goes.urlshortener.modules.user.presentation.dto.UserResponseDto;
import luis.goes.urlshortener.modules.user.shared.mapper.UserMapper;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserChangePassword implements IUserChangePassword {
    private final UserRepository repository;
    private final UserMapper mapper;

    public UserChangePassword(UserRepository repository, UserMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public UserResponseDto change(UUID id, UserChangePasswordDTO dto) {
        if (id == null) throw HttpException.badRequest("An error occurred: User ID was not provided.");

        UserEntity user = repository.findById(id)
                .orElseThrow(() -> HttpException.notFound("We couldn't find a user with the provided ID."));

        user.isPasswordMatches(user.getUserCredentials().getPassword().getValue(), dto.currentPassword());

        user.changePassword(dto.password(), dto.confirmPassword());

        return mapper.toDto(repository.save(user));
    }

}