package luis.goes.urlshortener.modules.authority.application.assignToUser;

import luis.goes.urlshortener.core.exception.HttpException;
import luis.goes.urlshortener.modules.authority.domain.AuthorityEntity;
import luis.goes.urlshortener.modules.authority.infrastructure.repository.AuthorityRepository;
import luis.goes.urlshortener.modules.authority.presentation.dto.AuthorityAssignToUseRequestDto;
import luis.goes.urlshortener.modules.authority.presentation.dto.AuthorityWithUserDto;
import luis.goes.urlshortener.modules.authority.shared.mapper.AuthorityMapper;
import luis.goes.urlshortener.modules.user.domain.UserEntity;
import luis.goes.urlshortener.modules.user.infrastructure.repository.UserRepository;
import luis.goes.urlshortener.modules.userAuthority.domain.UserAuthorityEntity;
import luis.goes.urlshortener.modules.userAuthority.infrastructure.repository.UserAuthorityRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AuthorityAssignToUser implements IAuthorityAssignToUser {

    private final AuthorityRepository authorityRepository;
    private final UserRepository userRepository;
    private final UserAuthorityRepository userAuthorityRepository;
    private final AuthorityMapper mapper;

    public AuthorityAssignToUser(AuthorityRepository authorityRepository, UserRepository userRepository,
                                 UserAuthorityRepository userAuthorityRepository, AuthorityMapper mapper) {
        this.authorityRepository = authorityRepository;
        this.userRepository = userRepository;
        this.userAuthorityRepository = userAuthorityRepository;
        this.mapper = mapper;
    }

    @Override
    public AuthorityWithUserDto assign(AuthorityAssignToUseRequestDto dto) {
        validateIds(dto.authorityId(), dto.userId());

        AuthorityEntity authority = findAuthority(dto.authorityId());
        UserEntity user = findUser(dto.userId());

        checkIfUserAlreadyHaveTheAuthority(authority, user);

        assignAuthorityToUser(user, authority);

        return mapper.toDto(authority, user);
    }

    private void validateIds(UUID authorityId, UUID userId) {
        if (authorityId == null) throw HttpException.badRequest("Authority ID must be provided.");
        if (userId == null) throw HttpException.badRequest("User ID must be provided.");
    }

    private AuthorityEntity findAuthority(UUID id) {
        return authorityRepository.findById(id)
                .orElseThrow(() -> HttpException.notFound("Authority not found with the provided ID."));
    }

    private UserEntity findUser(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> HttpException.notFound("User not found with the provided ID."));
    }

    private void assignAuthorityToUser(UserEntity user, AuthorityEntity authority) {
        UserAuthorityEntity userAuthority = new UserAuthorityEntity(user, authority);
        user.addAuthority(userAuthority);

        userRepository.save(user);
    }

    private void checkIfUserAlreadyHaveTheAuthority(AuthorityEntity authorityEntity, UserEntity userEntity) {
        if (userAuthorityRepository.findByAuthorityAndUser(authorityEntity, userEntity).isPresent()) throw HttpException.conflict("This user already have the authority");
    }

}