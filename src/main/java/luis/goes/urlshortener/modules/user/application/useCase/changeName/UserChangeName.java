package luis.goes.urlshortener.modules.user.application.useCase.changeName;

import luis.goes.urlshortener.core.exception.HttpException;
import luis.goes.urlshortener.modules.user.domain.UserEntity;
import luis.goes.urlshortener.modules.user.infrastructure.repository.UserRepository;
import luis.goes.urlshortener.modules.user.presentation.dto.UserChangeNameDTO;
import luis.goes.urlshortener.modules.user.presentation.dto.UserResponseDto;
import luis.goes.urlshortener.modules.user.shared.mapper.UserMapper;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserChangeName implements IUserChangeName {
    private final UserRepository repository;
    private final UserMapper mapper;

    public UserChangeName(UserRepository repository, UserMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public UserResponseDto change(UUID id, UserChangeNameDTO dto) {
        checkIfNameIsAlreadyInUse(dto);

        if (id == null) throw HttpException.badRequest("An error occurred: User ID was not provided.");

        UserEntity user = repository.findById(id)
                .orElseThrow(() -> HttpException.notFound("We couldn't find a user with the provided ID."));

        user.changeName(dto.name());

        return mapper.toDto(repository.save(user));
    }

    private void checkIfNameIsAlreadyInUse(UserChangeNameDTO dto) {
        if (repository.findByName_Name(dto.name()).isPresent()) throw HttpException.conflict("This name is already associated with another user.");
    }

}