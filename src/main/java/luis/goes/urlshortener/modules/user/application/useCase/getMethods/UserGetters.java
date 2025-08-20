package luis.goes.urlshortener.modules.user.application.useCase.getMethods;

import luis.goes.urlshortener.core.exception.HttpException;
import luis.goes.urlshortener.modules.user.domain.UserEntity;
import luis.goes.urlshortener.modules.user.infrastructure.repository.UserRepository;
import luis.goes.urlshortener.modules.user.presentation.dto.UserResponseDto;
import luis.goes.urlshortener.modules.user.presentation.dto.UserWithAuthorityDto;
import luis.goes.urlshortener.modules.user.shared.mapper.UserMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserGetters implements IUserGetters {
    private final UserRepository repository;
    private final UserMapper mapper;

    public UserGetters(UserRepository repository, UserMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public UserResponseDto byId(UUID id) {
        if (id == null) throw HttpException.badRequest("An error occurred: User ID was not provided.");

        UserEntity user = repository.findById(id)
                .orElseThrow(() -> HttpException.notFound("We couldn't find a user with the provided ID."));

        return mapper.toDto(user);
    }

    @Override
    public UserResponseDto byName(String name) {
        if (name == null) throw HttpException.badRequest("An error occurred: User name was not provided.");

        UserEntity user = repository.findByName_Name(name.trim())
                .orElseThrow(() -> HttpException.notFound("We couldn't find a user with the provided name."));

        return mapper.toDto(user);
    }

    @Override
    public UserResponseDto byEmail(String email) {
        if (email == null) throw HttpException.badRequest("An error occurred: User email was not provided.");

        UserEntity user = repository.findByUserCredentials_Email_Email(email.trim())
                .orElseThrow(() -> HttpException.notFound("We couldn't find a user with the provided email."));

        return mapper.toDto(user);
    }

    @Override
    public List<UserResponseDto> all() {
        return mapper.toDtoList(repository.findAll());
    }

    @Override
    public List<UserResponseDto> allDeactivated() {
        return mapper.toDtoList(repository.findAllByDateInfoDeletedAtNotNull());
    }

    @Override
    public UserWithAuthorityDto allUserAuthorities(UUID userId) {
        if (userId == null) throw HttpException.badRequest("User ID must be provided.");

        UserEntity user = repository.findById(userId)
                .orElseThrow(() -> HttpException.notFound("User not found with the provided ID."));

        return mapper.toDtoWithAuthority(user);
    }

}