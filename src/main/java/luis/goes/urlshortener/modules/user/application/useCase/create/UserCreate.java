package luis.goes.urlshortener.modules.user.application.useCase.create;

import luis.goes.urlshortener.core.exception.HttpException;
import luis.goes.urlshortener.modules.authority.domain.AuthorityEntity;
import luis.goes.urlshortener.modules.authority.domain.enums.AuthorityUrl;
import luis.goes.urlshortener.modules.authority.domain.enums.AuthorityUser;
import luis.goes.urlshortener.modules.authority.domain.enums.IAuthority;
import luis.goes.urlshortener.modules.authority.infrastructure.repository.AuthorityRepository;
import luis.goes.urlshortener.modules.user.domain.UserEntity;
import luis.goes.urlshortener.modules.user.infrastructure.repository.UserRepository;
import luis.goes.urlshortener.modules.user.presentation.dto.UserRequestDTO;
import luis.goes.urlshortener.modules.user.presentation.dto.UserResponseDto;
import luis.goes.urlshortener.modules.user.shared.mapper.UserMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

@Service
public class UserCreate implements IUserCreate {

    private final UserRepository repository;
    private final AuthorityRepository authorityRepository;
    private final UserMapper mapper;

    public UserCreate(UserRepository repository, AuthorityRepository authorityRepository, UserMapper mapper) {
        this.repository = repository;
        this.authorityRepository = authorityRepository;
        this.mapper = mapper;
    }

    @Override
    public final UserResponseDto create(UserRequestDTO dto) {
        checkIfDataIsAlreadyInUse(dto);

        List<AuthorityEntity> permissions = getDefaultPermissions();

        UserEntity user = new UserEntity(dto.name(), dto.email(), dto.password(), permissions);

        return mapper.toDto(repository.save(user));
    }

    private List<AuthorityEntity> getDefaultPermissions() {
        final List<AuthorityEntity> authorityEntityList = new ArrayList<>();

        var defaultUserPermissions = List.of(
                AuthorityUser.VIEW_PROFILE,
                AuthorityUser.CHANGE_PASSWORD,
                AuthorityUser.CHANGE_NAME,
                AuthorityUser.CHANGE_EMAIL,
                AuthorityUser.DEACTIVATE
        );

        var defaultUrlPermissions = List.of(
                AuthorityUrl.VIEW,
                AuthorityUrl.CREATE,
                AuthorityUrl.CHANGE_NAME,
                AuthorityUrl.CHANGE_URL,
                AuthorityUrl.DELETE
        );

        Stream.of(defaultUrlPermissions, defaultUserPermissions)
                .flatMap(Collection::stream)
                .map(this::findPermissionEntity)
                .forEach(authorityEntityList::add);


        return authorityEntityList;
    }

    private AuthorityEntity findPermissionEntity(IAuthority permission) {
        return authorityRepository.findByAuthorityName_Authority(permission.getValue())
                .orElseThrow(() -> HttpException.notFound("Permission not found: " + permission.getValue()));
    }

    private void checkIfDataIsAlreadyInUse(UserRequestDTO dto) {
        if (repository.findByName_Name(dto.name()).isPresent()) throw HttpException.conflict("This name is already associated with another account.");
        if (repository.findByUserCredentials_Email_Email(dto.email()).isPresent()) throw HttpException.conflict("This email address is already associated with another account.");
    }

}